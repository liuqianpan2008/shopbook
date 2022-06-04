package com.fenglin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fenglin.utils.Res;
import com.fenglin.domain.Book;
import com.fenglin.domain.User;
import com.fenglin.domain.Bookorder;
import com.fenglin.domain.Notice;
import com.fenglin.service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class Admin {
    @Autowired
    private  IuserService iuserService;
    @Autowired
    private com.fenglin.dao.UserDao userDao;
    @Autowired
    private com.fenglin.dao.BookDao bookDao;
    @Autowired
    private com.fenglin.service.impl.BookServicelmpl bookServicelmpl;
    @Autowired
    private com.fenglin.service.impl.NoticeServicelmpl noticeServicelmpl;
    @Autowired
    private com.fenglin.service.impl.OrderServicelmpl orderServicelmpl;
    @Autowired
    private com.fenglin.service.impl.UserServiceImpl userServiceImpl;
    //   账号登录
    @PostMapping("login")
    @CrossOrigin("*")
    public Res login(@RequestBody Map<String,String> data){
        int id = iuserService.login(data.get("User"),data.get("paw"));
        if (id>0){
            if (userDao.selectById(id).getAdmin().equals("受限")){
                return new Res(true,true,"账号受限!禁止登陆");
            }
            if (userDao.selectById(id).getAdmin().equals("是")){
                return new Res(true,true,"管理登陆成功");
            }else{
                return new Res(true,false,"非管理员禁止登陆");
            }
        }else {
            return new Res(true,false,"账号密码错误无法登陆");
        }
    }
//   修改账号密码
    @PostMapping("setpaw")
    @CrossOrigin("*")
    public Res setpaw(@RequestBody Map<String,String> data){
        int id = iuserService.login(data.get("User"),data.get("opaw"));
        if (id>0){
            User user = userDao.selectById(id);
            user.setUserPassword(data.get("npaw"));
            if (iuserService.updateById(user)){
                return new Res(true,true,"修改密码成功,请重新登录");
            }else{
                return new Res(true,false,"修改密码失败,请联系管理员！");
            }
        }else {
            return new Res(true,false,"账号旧密码错误！无法修改");
        }
    }
//   新增图书
    @PostMapping("Addbook")
    @CrossOrigin("*")
    public Res Addbook(@RequestBody Book book){
        if(bookServicelmpl.save(book)){
            return new Res(true,true,"添加图书成功！");
        }else{
            return new Res(true,false,"添加图书失败！请联系管理员");
        }

    }
    //   修改图书
    @PostMapping("setupbook")
    @CrossOrigin("*")
    public Res setupbook(@RequestBody Book book){
        if(bookServicelmpl.updateById(book)){
            return new Res(true,true,"修改图书成功！");
        }else{
            return new Res(true,false,"修改图书失败！请联系管理员");
        }

    }
//   删除图书
    @PostMapping("Dalbook")
    @CrossOrigin("*")
    public Res Dalbook(@RequestBody Book book){
        if(bookServicelmpl.removeById(book)){
            return new Res(true,true,"删除图书成功！");
        }else{
            return new Res(true,false,"删除图书失败！请联系管理员");
        }
    }

    //   新增公告
    @PostMapping("Addnotices")
    @CrossOrigin("*")
    public Res Addnotices(@RequestBody Notice notice){
        notice.setTime(new Date());
        if(noticeServicelmpl.save(notice)){
            return new Res(true,true,"添加公告成功！");
        }else{
            return new Res(true,false,"添加公告失败！请联系管理员");
        }

    }
    //   修改公告
    @PostMapping("setupnotices")
    @CrossOrigin("*")
    public Res setupnotices(@RequestBody Notice notice){
        if(noticeServicelmpl.updateById(notice)){
            return new Res(true,true,"修改公告成功！");
        }else{
            return new Res(true,false,"修改公告失败！请联系管理员");
        }

    }
    //   删除公告
    @PostMapping("Dalnotices")
    @CrossOrigin("*")
    public Res Dalnotices(@RequestBody Notice notice){
        if(noticeServicelmpl.removeById(notice)){
            return new Res(true,true,"删除公告成功！");
        }else{
            return new Res(true,false,"删除公告失败！请联系管理员");
        }
    }
//  订单查询
    @CrossOrigin("*")
    @PostMapping("orderall")
    public Res orderall(){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        orderServicelmpl.list().forEach(v->{
            Map<String,String> map=new HashMap<>();
            map.put("orderid",v.getOrderid());
            map.put("username",userDao.selectById(v.getUserid()).getUserName());
            getRorder(list, v, map);
        });
        return new Res(true,list,"获取成功");
    }
//   根据用户名,订单查询
    @CrossOrigin("*")
    @PostMapping("orderallQ")
    public Res orderallQ(@RequestBody Map<String,String> data){
//      查找用户id
        QueryWrapper qwUser = new QueryWrapper<User>();
        qwUser.eq("user_name", data.get("username"));
        User user1=userDao.selectOne(qwUser);
//      根据id进行用户查找
        QueryWrapper qworder = new QueryWrapper<Bookorder>();
        qworder.eq("userid", user1.getId());
        List<Bookorder> order=orderServicelmpl.list(qworder);
//      遍历重构返回值
        ArrayList<Map<String,String>> list=new ArrayList<>();
        order.forEach(v->{
            Map<String,String> map=new HashMap<>();
            map.put("orderid",v.getOrderid());
            map.put("username",user1.getUserName());
            getRorder(list, v, map);
        });
        return new Res(true,list,"获取成功");
    }

    private void getRorder(ArrayList<Map<String, String>> list, Bookorder v, Map<String, String> map) {
        map.put("bookname",bookDao.selectById(v.getBookid()).getName());
        map.put("num", String.valueOf(v.getNum()));
        map.put("name", String.valueOf(v.getName()));
        map.put("phone", String.valueOf(v.getPhone()));
        map.put("addr", String.valueOf(v.getAddr()));
        map.put("cod", String.valueOf(v.getCod()));
        list.add(map);
    }
//   账号状态管理设置
    @CrossOrigin("*")
    @PostMapping("setupuseradmin")
    public Res setupuseradmin(@RequestBody User user){
        user.setAdmin("是");
       if(userDao.updateById(user)>0){
           return new Res(true,true,"更改成功");
       }else{
           return new Res(true,false,"更改失败，请联系管理员！");
       }

    }
    @CrossOrigin("*")
    @PostMapping("setupuserban")
    public Res setupuserban(@RequestBody User user){
        user.setAdmin("受限");
        if(userDao.updateById(user)>0){
            return new Res(true,true,"更改成功");
        }else{
            return new Res(true,false,"更改失败，请联系管理员！");
        }
    }
    @CrossOrigin("*")
    @PostMapping("setupuserunadmin")
    public Res setupuserunadmin(@RequestBody User user){
        user.setAdmin("否");
        if(userDao.updateById(user)>0){
            return new Res(true,true,"更改成功");
        }else{
            return new Res(true,false,"更改失败，请联系管理员！");
        }
    }
    @CrossOrigin("*")
    @PostMapping("index")
    public Res index(){
        Map<String, Long> map=new HashMap<String,Long>();
        map.put("user",userServiceImpl.count());
        map.put("order",orderServicelmpl.count());
        map.put("nodtic",noticeServicelmpl.count());
        map.put("book",bookServicelmpl.count());
        return new Res(true,map,"获取数据成功");
    }
}
