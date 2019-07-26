package com.fisher.web.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ShiroSessionDao extends AbstractSessionDAO {

    private RedisTemplate redisTemplate;

    public ShiroSessionDao(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 创建 session id 并存入 redis
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        // 生成 session id
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        redisTemplate.boundHashOps("sys_session").put(sessionId.toString(), session);
        return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        if(serializable == null){
            return null;
        }
        Session session = (Session) redisTemplate.boundHashOps("sys_session").get(serializable.toString());
        if(session == null){
            return null;
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        redisTemplate.boundHashOps("sys_session").put(session.getId().toString(),session);
    }

    @Override
    public void delete(Session session) {
        redisTemplate.boundHashOps("sys_session").delete(session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        List<Session> sessions = redisTemplate.boundHashOps("sys_session").values();
        return new HashSet<Session>(sessions);
//        return null;
    }
}
