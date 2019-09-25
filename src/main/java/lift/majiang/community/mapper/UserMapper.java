package lift.majiang.community.mapper;

import lift.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//mapper是和数据库相互连接的。而我们的dto是和远端的api调用相互取值的。
@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})" )
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);//当查询给的参数不是一个类的时候。我们需要@Param（）的形式来写
}
