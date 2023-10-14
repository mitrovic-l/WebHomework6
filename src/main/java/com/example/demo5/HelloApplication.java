package com.example.demo5;

import com.example.demo5.repositories.comment.CommentRepository;
import com.example.demo5.repositories.post.PostRepository;
import com.example.demo5.repositories.user.UserRepository;
import com.example.demo5.repositories.comment.MySqlCommentRepository;
import com.example.demo5.repositories.post.MySqlPostRepository;
import com.example.demo5.repositories.user.InMemoryUserRepository;
import com.example.demo5.services.CommentService;
import com.example.demo5.services.PostService;
import javax.ws.rs.ApplicationPath;
import javax.inject.Singleton;


import com.example.demo5.services.UserService;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication(){
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlPostRepository.class).to(PostRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(InMemoryUserRepository.class).to(UserRepository.class).in(Singleton.class);

                this.bindAsContract(PostService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(UserService.class);

            }
        };
        register(binder);
        packages("com.example.demo5");
    }

}