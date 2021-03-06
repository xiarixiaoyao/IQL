package cn.i4.report.iql.service;

import cn.i4.report.bean.SaveIql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

public interface SaveIqlRepository extends JpaRepository<SaveIql, Long> {

    //原生SQL实现更新方法接口
    @Query(value = "update save_iql set iql=?1,mode=?2,name=?3,description=?4,update_time=?5 where id=?6", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateOne(String iql, String mode, String name, String description, Timestamp updateTime, int id);

}
