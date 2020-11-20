package com.gm.webadmin.loginserv.service.impl;

import com.gm.webadmin.common.entity.PageRequest;
import com.gm.webadmin.common.entity.PageResult;
import com.gm.webadmin.common.utils.CryptionUtil;
import com.gm.webadmin.common.utils.MybatisPageHelper;
import com.gm.webadmin.common.utils.PoiUtils;
import com.gm.webadmin.common.utils.UUIDUtil;
import com.gm.webadmin.loginserv.dao.LoginDAO;
import com.gm.webadmin.loginserv.dto.UserDTO;
import com.gm.webadmin.loginserv.entity.User;
import com.gm.webadmin.loginserv.service.LoginServ;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LoginServImpl implements LoginServ {


    public User packageUser(UserDTO dto){
        User user = new User();
        user.setState(0);
        user.setAccountnum(dto.getAccount());
        user.setPassword(dto.getPassword());
        return user;
    }
    @Autowired
    LoginDAO loginDao;

    @Override
    public boolean doLogin(UserDTO userdto) {
        User user = packageUser(userdto);
        int state = 0;
        User localuser = loginDao.getUser(user.getAccountnum());
//        PageResult result = MybatisPageHelper.findPage(new PageRequest(),loginDao,"getUser",user.getAccountnum());
//        User localuser = (User) result.getContent().get(0);
        if(localuser==null){
            return false;
        }else{
            if(new String(CryptionUtil.base64Decrypt(localuser.getPassword().getBytes())).equals(user.getPassword())){
                user.setId(localuser.getId());
                user.setState(1);
                user.setUpdateTime(new Date());
                state = loginDao.updUserforlogin(user);
            }
        }

        return state>0?true:false;
    }

    @Override
    public boolean registerUser(UserDTO userdto) {
        User user = packageUser(userdto);
        User localuser = loginDao.getUser(user.getAccountnum());
        int num = 0;
        if(localuser==null){
            user.setId(UUIDUtil.getUUID());
            user.setPassword(new String(CryptionUtil.base64Encrypt(user.getPassword())));
            user.setCreateTime(new Date());
            num = loginDao.addUser(user);
        }
        return num>0?true:false;
    }

    @Override
    public int save(Object record) {
        return 0;
    }

    @Override
    public int delete(Object record) {
        return 0;
    }

    @Override
    public int delete(List records) {
        return 0;
    }

    @Override
    public Object findById(String id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        PageResult pageResult = null;
//        Object name = pageRequest.getParam("name");
//        Object email = pageRequest.getParam("email");
//        if(name != null) {
//            if(email != null) {
//                pageResult = MybatisPageHelper.findPage(pageRequest, loginDao, "findPageByNameAndEmail", name, email);
//            } else {
//                pageResult = MybatisPageHelper.findPage(pageRequest, loginDao, "findPageByName", name);
//            }
//        } else {

            pageResult = MybatisPageHelper.findPage(pageRequest, loginDao);

//        }
        return pageResult;
    }

    @Override
    public File createUserExcelFile(PageRequest pageRequest) {
        PageResult pageResult = findPage(pageRequest);
        return createUserExcelFile(pageResult.getContent());
    }

    public static File createUserExcelFile(List<?> records) {
        if (records == null) {
            records = new ArrayList<>();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row0 = sheet.createRow(0);
        int columnIndex = 0;
        row0.createCell(columnIndex).setCellValue("No");
        row0.createCell(++columnIndex).setCellValue("ID");
        row0.createCell(++columnIndex).setCellValue("用户名");
        row0.createCell(++columnIndex).setCellValue("账户");
        row0.createCell(++columnIndex).setCellValue("密码");
        row0.createCell(++columnIndex).setCellValue("状态");
        row0.createCell(++columnIndex).setCellValue("创建时间");
        row0.createCell(++columnIndex).setCellValue("更新时间");
        row0.createCell(++columnIndex).setCellValue("备注");
        for (int i = 0; i < records.size(); i++) {
            User user = (User) records.get(i);
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < columnIndex + 1; j++) {
                row.createCell(j);
            }
            columnIndex = 0;
            row.getCell(columnIndex).setCellValue(i + 1);
            row.getCell(++columnIndex).setCellValue(user.getId());
            row.getCell(++columnIndex).setCellValue(user.getName());
            row.getCell(++columnIndex).setCellValue(user.getAccountnum());
            row.getCell(++columnIndex).setCellValue(user.getPassword());
            row.getCell(++columnIndex).setCellValue(user.getState());
            row.getCell(++columnIndex).setCellValue(user.getCreateTime());
            row.getCell(++columnIndex).setCellValue(user.getUpdateTime());
            row.getCell(++columnIndex).setCellValue(user.getRemark());
        }
        return PoiUtils.createExcelFile(workbook, "download_user");
    }
}
