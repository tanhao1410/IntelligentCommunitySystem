package cn.practice.community.mapper;


import cn.practice.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}