package com.example.demo5.repositories.comment;

import com.example.demo5.entities.Comment;
import com.example.demo5.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {
    @Override
    public List<Comment> allCommentsForPost(Integer postId) {
        List<Comment> comments = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Comment where postId = " + postId);
            while (resultSet.next()){
                comments.add( new Comment(resultSet.getInt("id"), postId, resultSet.getString("name"), resultSet.getString("text")));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comments;
    }

    @Override
    public Comment addComent(Comment comment, Integer postId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement("INSERT into Comment (name, text, postId) values (?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getName());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setInt(3, comment.getPostId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                comment.setId(resultSet.getInt(0));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }
}
