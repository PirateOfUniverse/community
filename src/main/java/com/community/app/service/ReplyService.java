package com.community.app.service;

import com.community.app.domain.Reply;
import com.community.app.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService implements ReplyMapper {

    @Autowired
    ReplyMapper mapper;

    @Override
    public int writeReply(Reply reply) {
        return mapper.writeReply(reply);
    }

    @Override
    public int writeReReply(Reply reply) {
        return mapper.writeReReply(reply);
    }

    @Override
    public int updateReply(Reply reply) {
        return mapper.updateReply(reply);
    }

    @Override
    public Integer selectReplyPidx(int ridx) {
        return mapper.selectReplyPidx(ridx);
    }

    @Override
    public int deleteReply(int ridx) {
        return mapper.deleteReply(ridx);
    }
}
