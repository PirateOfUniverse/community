package com.community.app.mapper;

import com.community.app.domain.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReplyMapper {
    public int writeReply(Reply reply);
    public int writeReReply(Reply reply);
    public int updateReply(Reply reply);
    public Integer selectReplyPidx(int ridx);
    public int deleteReply(@Param("ridx") int ridx);
}
