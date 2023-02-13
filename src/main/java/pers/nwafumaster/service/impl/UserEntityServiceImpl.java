package pers.nwafumaster.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.nwafumaster.beans.UserEntity;
import pers.nwafumaster.service.UserEntityService;
import pers.nwafumaster.mapper.UserEntityMapper;
import org.springframework.stereotype.Service;
import pers.nwafumaster.vo.UserEntityForExc;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Windlinxy
 */
@Service
public class UserEntityServiceImpl extends ServiceImpl<UserEntityMapper, UserEntity>
        implements UserEntityService {

    @Resource
    private UserEntityMapper userEntityMapper;

    @Override
    public void excelExport(HttpServletResponse response) throws IOException {
        List<UserEntityForExc> list = new ArrayList<>();
        userEntityMapper.selectList(null).forEach(item -> list.add(new UserEntityForExc(item)));
        String fileName = "用户行为";
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls");
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS, true);
        Sheet sheet = new Sheet(1, 0, UserEntityForExc.class);
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        sheet.setSheetName("用户行为");
        writer.write(list, sheet);
        writer.finish();
        out.flush();
        response.getOutputStream().close();
        out.close();
    }
}




