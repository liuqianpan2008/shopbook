package com.fenglin.controller

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.stp.StpUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.fenglin.utils.Res
import com.fenglin.dao.OrderDao
import com.fenglin.dao.BookDao
import com.fenglin.dao.UserDao
import com.fenglin.domain.User
import com.fenglin.domain.Bookorder
import com.fenglin.domain.shop
import com.fenglin.service.impl.MailServiceImpl
import com.fenglin.service.impl.ShopServicelmpl
import com.fenglin.service.impl.OrderServicelmpl
import com.fenglin.service.impl.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.TimeUnit


@RestController
@CrossOrigin("*")
@RequestMapping("/users")
class User(
    @Autowired
    internal val userService: UserServiceImpl,
    @Autowired
    internal val MailServiceImpl: MailServiceImpl,
    @Autowired
    internal val RedisTemplate: StringRedisTemplate,
    @Autowired
    internal val userDao: UserDao,
    @Autowired
    internal val shopServicelmpl: ShopServicelmpl,
    @Autowired
    internal val orderServicelmpl: OrderServicelmpl,
    @Autowired
    internal val bookDao: BookDao,
    @Autowired
    internal val orderDao: OrderDao,
) {
    //用户登录
    @PostMapping("login")
    @CrossOrigin("*")
    fun login(@RequestBody data: Map<String,String>):Res{
        val userName= data["userName"] ?:""
        val userPassword= data["userPassword"] ?:""
//      后端验证
        if (userName.length !in (3..5) || userPassword.length !in (3..10)){
            val r= mapOf("cod" to  false)
            return Res(msg = "输入大小错误！", date = r)
        }
        val id = userService.login(userName,userPassword)
    return if (id>0) {
            if (userDao.selectById(id).admin == "受限"){
                val r= mapOf("cod" to "false")
                Res(msg = "账户受限禁止登陆", date = r)
            }else{
                StpUtil.login(id)
                val r= mapOf("cod" to  "true","token" to StpUtil.getTokenValue())
                Res(msg = "登录成功", date = r)
            }
        } else {
            val r= mapOf("cod" to "false")
            Res(msg = "登录失败,账号或者密码有误", date = r)
        }
    }
//  用户退出登录
    @SaCheckLogin
    @PostMapping("loginout")
    fun loginout():Res{
        StpUtil.logout()
        return Res(date = true, msg = "退出成功")
    }
//用户注册
    @PostMapping("register")
    fun register(@RequestBody user: User):Res{
//   后端验证
    if (user.userName.length !in (3..5) || user.userPassword.length !in (3..10)){
        val r= mapOf("cod" to  false)
        return Res(msg = "输入错误！", date = r)
    }
    seedCodMail(user.userName,user.mail)
    return if (userService.save(user)) Res(msg = "注册成功,请取邮箱激活", date = true) else Res(msg = "注册失败,请取邮箱激活", date = false)
    }
//查看用户信息
    @SaCheckLogin
    @CrossOrigin("*")
    @GetMapping("userInfo")
    fun userInfo():Res {
        val id = StpUtil.getLoginIdAsInt()
        return Res(msg = "查询成功！",date = userService.getById(id))
    }
//  购物车系统
    @SaCheckLogin
    @CrossOrigin("*")
    @GetMapping("shopInfo")
    fun shopInfo():Res {
        val id = StpUtil.getLoginIdAsInt()
        val shopQw = QueryWrapper<shop>()
        shopQw.eq("userid", id)
        val shops=shopServicelmpl.list(shopQw)
        shops?.let { return Res(msg = "查询成功！",date = it); }
        return Res(msg = "查无数据！")
    }
//   添加购物车
    @SaCheckLogin
    @CrossOrigin("*")
    @PostMapping("addshop")
    fun addshop(@RequestBody data: Map<String, String>):Res {
        val id = StpUtil.getLoginIdAsInt()
        val name = data["name"]?:""
//  判断购物车是否拥有该商品
        val shopQw = QueryWrapper<shop>()
        shopQw.eq("userid", id)
        val shops=shopServicelmpl.list(shopQw)
        shops.forEach {
            if (name == it.name) return Res(date = false, msg = "该商品已经存在于购物车中")
        }
//  添加购物车
        val unitprice = data["unitprice"]?.toInt()?:0
        val quantity = data["quantity"]?.toInt()?:0
        return if (shopServicelmpl.save(shop(
            null,id,name,unitprice,quantity,unitprice*quantity))){
            Res(date = true, msg = "添加成功")
        } else{
            Res(date = false, msg = "添加失败")
        }
    }
//  删除购物车
    @SaCheckLogin
    @CrossOrigin("*")
    @PostMapping("dalshop")
    fun dalshop(@RequestBody shop: shop):Res {
        val id = StpUtil.getLoginIdAsInt()
        val shopQw = QueryWrapper<shop>()
        shopQw.eq("name", shop.name)
        shopQw.eq("userid",id)
        if(!shopServicelmpl.remove(shopQw)){return Res(date = false, msg = "删除过程出现错误！")}
        return Res(date = true, msg = "删除过程出现错误！")
    }
//  添加一笔订单
    @SaCheckLogin
    @CrossOrigin("*")
    @PostMapping("orderadd")
    fun orderadd(@RequestBody data: Map<String, String>): Res {
        val bookname = data["bookname"]?:""
        val bookQw = QueryWrapper<com.fenglin.domain.Book>()
        bookQw.eq("name", bookname)
        //   查询之前是否已添加过订单
         bookDao.selectOne(bookQw)?.id?.let {
             val orderQw = QueryWrapper<Bookorder>()
             orderQw.eq("bookid",it)
             orderQw.eq("userid",StpUtil.getLoginId()?.toString())
             if(orderDao.selectOne(orderQw) != null) return Res(date = false, msg = "你已经添加过这个订单了！")
         }
        //        添加订单
        val bookorder = Bookorder(
            data["bookid"] ?: "",
            StpUtil.getLoginId()?.toString()?.toInt() ?: 0,
            bookDao.selectOne(bookQw)?.id ?: 0,
            data["num"]?.toInt() ?: 0,
            data["name"] ?: "",
            data["phone"] ?: "",
            data["addr"] ?: ""
        )
        return if (orderServicelmpl.save(bookorder)) Res(date = true, msg = "添加订单成功！") else Res(date = false, msg = "添加订货失败！")
    }
    //  查看一笔订单
    @SaCheckLogin
    @CrossOrigin("*")
    @PostMapping("orderinfo")
    fun orderinfo(@RequestBody data: Map<String, String>): Res {
        val bookid = data["bookid"]?:""
        val bookQw = QueryWrapper<Bookorder>()
        bookQw.eq("userid", StpUtil.getLoginId()?.toString()?.toInt()?:0)
        bookQw.eq("orderid", bookid)
        val books = orderDao.selectList(bookQw)
        books.forEach {
            it.book = bookDao.selectById(it.bookid)
        }
        return Res(date = books)
    }
//   查看用户订单
    @SaCheckLogin
    @CrossOrigin("*")
    @PostMapping("orderuser")
    fun orderuser(): Res {
        val orderQw = QueryWrapper<Bookorder>()
        orderQw.eq("userid", StpUtil.getLoginId()?.toString()?.toInt()?:0)
        var orders = orderDao.selectList(orderQw);
        //查书
        orders.forEach {
            val bookQw = QueryWrapper<com.fenglin.domain.Book>()
            bookQw.eq("id",it.bookid)
            it.book=bookDao.selectOne(bookQw);
        }
        return Res(date = orders);
    }
//  修改订单(修改快递)
    @SaCheckLogin
    @CrossOrigin("*")
    @PostMapping("orderput")
    fun orderput(@RequestBody data: Map<String, String>): Res {
        val bookid = data["bookid"]?:""
        val bookQw = QueryWrapper<Bookorder>()
        bookQw.eq("userid", StpUtil.getLoginId()?.toString()?.toInt()?:0)
        bookQw.eq("orderid", bookid)
        orderDao.selectList(bookQw).forEach{
//                if (it.cod != "未支付" || it.cod != "已支付") return Res(date = false, msg = "已发货无法修改")
                it.name=data["name"]?:""
                it.phone=data["phone"]?:""
                it.addr=data["addr"]?:""
                if (orderDao.updateById(it)<=0){
                    return  Res(date = false, msg = "快递填写失败！")
                }
        }
        return  Res(date = true, msg = "快递填写成功！")
    }
    //  修改订单(购买成功)
    @SaCheckLogin
    @CrossOrigin("*")
    @PostMapping("paysee")
    fun paysee(@RequestBody data: Map<String, String>): Res {
        val bookid = data["bookid"]?:""
        val bookQw = QueryWrapper<Bookorder>()
        bookQw.eq("userid", StpUtil.getLoginId()?.toString()?.toInt()?:0)
        bookQw.eq("orderid", bookid)
        orderDao.selectList(bookQw).forEach{
//            if (it.cod !="未支付" || it.cod != "已支付") return Res(date = false, msg = "已发货无法修改")
            it.cod = data["cod"]?:""
            if (orderDao.updateById(it)<=0){
                return  Res(date = false, msg = "支付失败！请稍后再试")
            }
        }
        return  Res(date = true, msg = "支付成功！请稍后再试")
    }
//  邮件验证
    @CrossOrigin("*")
    @PostMapping("/MailCod")
    fun mailCod(@RequestBody data: Map<String,String>): Res {
        val cod = data["username"]?.let { RedisTemplate.opsForValue().get(it) }//读取缓存
    return if (cod== data["cod"]){
        val qwUser = QueryWrapper<User>()
        qwUser.eq("user_name", data["username"])
        val user1 = userDao.selectOne(qwUser)
        user1.mailcod = "验证"
        if (userService.updateById(user1))  Res(msg = "验证成功" , date = true) else Res(msg = "写入数据库失败,验证失败！" , date = false)
    }else{
        Res(msg = "验证失败!请重新验证！" , date = false)
    }
    }
//  再次验证
    @CrossOrigin("*")
    @PostMapping("/agSeedCodMail")
    fun agSeedCodMail(@RequestBody data: Map<String,String>):Res{
        seedCodMail(data["username"]?:"",data["mail"]?:"")
        return Res(date = true, msg = "验证码发送成功请前往邮箱查看")
    }
//修改密码
    @SaCheckLogin
    @CrossOrigin("*")
    @PostMapping("/seedCodPAW")
    fun seedCodPAW(@RequestBody data: Map<String,String>):Res{
        val uuid = UUID.randomUUID().toString().replace("-".toRegex(), "")//生成UUID
        StpUtil.getLoginIdAsInt()?.let { RedisTemplate.opsForValue().set(it.toString(), uuid,60*5, TimeUnit.SECONDS); }//存入缓存
        data["mail"]?.let {
            MailServiceImpl.SeedMail(it,"书城项目注册邮箱验证","感谢你注册书城项目，下面这是你的验证码：${uuid}")
            return Res(date = true, msg = "发送成功！")
        }//发送验证链接进行邮箱验证
        return Res(date = false, msg = "发送失败！")
    }
//  修改密码
    @SaCheckLogin
    @CrossOrigin("*")
    @PostMapping("/xgPAW")
    fun xgPAW(@RequestBody data: Map<String,String>):Res{
        val id = StpUtil.getLoginId().toString()
//      校验验证码
        val cod  = id?.let { RedisTemplate.opsForValue().get(it) }
        if (data["cod"] == cod){
            val qwUser = QueryWrapper<User>()
            qwUser.eq("id",id)
            var seuser = userDao.selectOne(qwUser)
            seuser.userPassword = data["passworld"]?:""
            return if (userDao.updateById(seuser)>0) Res(date = true, msg = "修改密码成功！") else Res(date = false, msg = "修改密码失败！")
        }
        return Res(date = false, msg = "验证码不正确！")


    }

    fun seedCodMail(username:String? , mail:String?){
        val uuid = UUID.randomUUID().toString().replace("-".toRegex(), "")//生成UUID
        username?.let { RedisTemplate.opsForValue().set(it, uuid,60*5, TimeUnit.SECONDS); }//存入缓存
        mail?.let {
            MailServiceImpl.SeedMail(it,"书城项目注册邮箱验证","感谢你注册书城项目，下面这是你的验证链接http://localhost:8080/#/mailcod/${username}/${uuid}")
        }//发送验证链接进行邮箱验证
    }
//   生成二维码
    @CrossOrigin("*")
    @GetMapping("/logincod")
    fun logincod():Res {
        //生成uuid
        val uuid = UUID.randomUUID().toString().replace("-".toRegex(), "")//生成UUID
        RedisTemplate.opsForValue().set(uuid, "-1",60*5, TimeUnit.SECONDS) //存入缓存
        return Res(date = uuid, msg = "生成成功！")
    }
    //  网页心跳验证！
    @CrossOrigin("*")
    @PostMapping("/verificationCod")
    fun verificationCod(@RequestBody data: Map<String,String>): Res {
        data["cod"]?.let {
            if (!RedisTemplate.hasKey(it)){
                return Res(date = mapOf("cod" to false), msg = "二维码过期了")
            }
        }
        val cod = data["cod"]?.let { RedisTemplate.opsForValue().get(it) }
        cod?.let {
            when(it){
                "-1" -> { return Res(date = mapOf("cod" to false), msg = "扫码等待中！")}
                "0"  -> { return Res(date = mapOf("cod" to false), msg = "已扫码")}
                else -> { return Res(date = mapOf("cod" to true , "data" to cod ), msg = "已登录")}
            }
        }
        return Res(flag = false, date=false, msg = "内部错误")
    }
    //  手机扫码校验
    @CrossOrigin("*")
    @PostMapping("/PhoneVerification")
    fun phoneVerification(@RequestBody data: Map<String,String>): Res {
        data["cod"]?.let {
            if (!RedisTemplate.hasKey(it)){
                return Res(date = false, msg = "二维码过期了")
            }
        }
        val cod = data["cod"]?.let { RedisTemplate.opsForValue().get(it) }
        cod?.let {
            data["cod"]?.let { it1 -> RedisTemplate.opsForValue().set(it1,"0") }
            return Res(date = true, msg = "手机端已扫码")
        }
        return Res(flag = false, date=false, msg = "内部错误")
    }
    //  手机端登录
    @CrossOrigin("*")
    @PostMapping("/PhoneLoginCod")
    fun phoneLoginCod(@RequestBody data: Map<String,String>): Res {
        data["cod"]?.let {
            if (!RedisTemplate.hasKey(it)){
                return Res(date = mapOf("cod" to false), msg = "二维码过期了")
            }
        }
        val cod = data["cod"]?.let { RedisTemplate.opsForValue().get(it) }
        if (cod=="0"){
            val userName=data.get("userName")?:""
            val userPassword=data.get("userPassword")?:""
//          内部登录
            val id = userService.login(userName,userPassword)
            return if (id>0) {
                StpUtil.login(id)
                data["cod"]?.let { RedisTemplate.opsForValue().set(it,StpUtil.getTokenValue()) }
                Res(msg = "登录成功", date = true)
            } else {
                Res(msg = "登录失败,账号或者密码有误", date = false)
            }
        }
        return Res(flag = false, date=false, msg = "内部错误")
    }
//  查询用户
    @CrossOrigin("*")
    @PostMapping("/userallq")
    fun userallq(@RequestBody data: Map<String,String>): Res {
        val qwUser = QueryWrapper<User>()
        data["search"]?.let {
            if (it != "") {
                qwUser.like("user_name", it)
            }
        }
        val iPage: IPage<User> = Page(data["current"]?.toLong()?:0, data["size"]?.toLong()?:0)
        val notices=userDao.selectPage(iPage,qwUser)
        notices?.let {
            return Res(date = it, msg = "获取成功！")
        }
        return Res(flag = false, msg = "无数据！")
    }
}