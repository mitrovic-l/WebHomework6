package com.example.demo5.repositories.post;

import com.example.demo5.entities.Post;
import com.example.demo5.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlPostRepository extends MySqlAbstractRepository implements PostRepository {
    @Override
    public List<Post> allPosts() {
        List<Post> posts = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Post");
            while (resultSet.next()){
                Post res = new Post(resultSet.getInt("id"), resultSet.getString("author"), resultSet.getString("title"), resultSet.getString("content"));
                res.setDate(resultSet.getString("date"));
                posts.add(res);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return posts;
    }

    @Override
    public Post addPost(Post post) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement("INSERT INTO Post (author, title, content, date) VALUES (?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, post.getAuthor());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getContent());
            preparedStatement.setString(4, post.getDate());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                post.setId(resultSet.getInt(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return post;
    }

    @Override
    public Post findPost(Integer id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Post post = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Post where id = " + id);
            while (resultSet.next()){
                post = new Post(resultSet.getInt("id"), resultSet.getString("author"), resultSet.getString("title"), resultSet.getString("content"));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return post;
    }

    @Override
    public void deletePost(Integer id) {

    }
}
