package com.epam.adok.core.service;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import javax.sql.DataSource;

import static com.ninja_squad.dbsetup.Operations.*;

public class GivenTestDataPreparer {

    public static void prepareBlogWithTwoFilledRootCommentBranchesAndBlogWithoutComments(DataSource dataSource) {

        Operation operation;
        operation = sequenceOf(
                deleteAllFrom("blog_category_assignment", "comment", "blog"),
                insertInto("blog")
                        .columns("id", "title", "content", "user_id", "publication_date")
                        .values(1L, "Blog#1", "Content Text", 1L, "2017-10-31 16:40:31")
                        .values(4L, "Blog#2", "Content Text", 1L, "2017-10-31 16:40:31")
                        .build(),
                insertInto("comment")
                        .columns("id", "blog_id", "parent_comment_id", "user_id", "text", "comment_date", "comment_type")
                        .values(1L, 1L, null, 1L, "BlogComment Text id = 1", "2017-10-31 13:00:00", "BT")
                        .values(2L, 1L, 1L, 2L, "BlogComment Text id = 2", "2017-10-31 13:30:00", "BT")
                        .values(3L, 1L, 1L, 2L, "BlogComment Text id = 3", "2017-10-31 13:40:00", "BT")
                        .values(4L, 1L, 3L, 1L, "BlogComment Text id = 4", "2017-10-31 13:45:00", "BT")
                        .values(5L, 1L, 1L, 2L, "BlogComment Text id = 5", "2017-10-31 13:50:00", "BT")
                        .values(6L, 1L, 5L, 1L, "BlogComment Text id = 6", "2017-10-31 14:00:00", "BT")
                        .values(7L, 1L, 5L, 1L, "BlogComment Text id = 7", "2017-10-31 14:15:00", "BT")
                        .values(8L, 1L, 5L, 1L, "BlogComment Text id = 8", "2017-10-31 14:20:00", "BT")
                        .values(9L, 1L, 5L, 1L, "BlogComment Text id = 9", "2017-10-31 14:40:00", "BT")
                        .values(10L, 1L, 3L, 1L, "BlogComment Text id = 10", "2017-10-31 14:55:00", "BT")
                        .values(11L, 1L, 2L, 2L, "BlogComment Text id = 11", "2017-10-31 15:10:00", "BT")
                        .values(12L, 1L, 1L, 1L, "BlogComment Text id = 12", "2017-10-31 15:40:00", "BT")
                        .values(13L, 1L, 5L, 2L, "BlogComment Text id = 13", "2017-10-31 16:40:00", "BT")
                        .values(14L, 1L, 6L, 1L, "BlogComment Text id = 14", "2017-10-31 16:50:00", "BT")
                        .values(15L, 1L, 7L, 2L, "BlogComment Text id = 15", "2017-10-31 17:40:00", "BT")
                        .values(16L, 1L, 3L, 1L, "BlogComment Text id = 16", "2017-10-31 17:50:00", "BT")
                        .values(17L, 1L, 2L, 2L, "BlogComment Text id = 17", "2017-10-31 18:20:00", "BT")
                        .values(18L, 1L, 3L, 1L, "BlogComment Text id = 18", "2017-10-31 18:25:00", "BT")
                        .values(19L, 1L, 4L, 2L, "BlogComment Text id = 19", "2017-10-31 19:00:00", "BT")
                        .values(20L, 1L, 6L, 1L, "BlogComment Text id = 20", "2017-10-31 19:20:00", "BT")
                        .values(21L, 1L, 1L, 2L, "BlogComment Text id = 21", "2017-10-31 19:30:00", "BT")
                        .values(22L, 1L, 2L, 1L, "BlogComment Text id = 22", "2017-10-31 19:40:00", "BT")
                        .values(23L, 1L, null, 1L, "BlogComment Text id = 23", "2017-10-31 13:20:00", "BT")
                        .values(24L, 1L, 23L, 2L, "BlogComment Text id = 24", "2017-10-31 13:35:00", "BT")
                        .values(25L, 1L, 24L, 2L, "BlogComment Text id = 25", "2017-10-31 19:45:00", "BT")
                        .values(26L, 1L, 24L, 1L, "BlogComment Text id = 26", "2017-10-31 19:45:00", "BT")
                        .values(27L, 1L, 23L, 2L, "BlogComment Text id = 27", "2017-10-31 13:55:00", "BT")
                        .values(28L, 1L, 26L, 1L, "BlogComment Text id = 28", "2017-10-31 14:30:00", "BT")
                        .values(29L, 1L, 27L, 1L, "BlogComment Text id = 29", "2017-10-31 19:20:00", "BT")
                        .values(30L, 1L, 29L, 1L, "BlogComment Text id = 30", "2017-10-31 14:20:50", "BT")
                        .values(31L, 1L, 28L, 1L, "BlogComment Text id = 31", "2017-10-31 14:40:40", "BT")
                        .values(32L, 1L, 26L, 1L, "BlogComment Text id = 32", "2017-10-31 14:55:20", "BT")
                        .values(33L, 1L, 29L, 2L, "BlogComment Text id = 33", "2017-10-31 15:10:30", "BT")
                        .values(34L, 1L, 30L, 1L, "BlogComment Text id = 34", "2017-10-31 15:40:50", "BT")
                        .values(35L, 1L, 31L, 2L, "BlogComment Text id = 35", "2017-10-31 16:40:30", "BT")
                        .values(36L, 1L, 33L, 1L, "BlogComment Text id = 36", "2017-10-31 16:50:20", "BT")
                        .values(37L, 1L, 33L, 2L, "BlogComment Text id = 37", "2017-10-31 17:40:30", "BT")
                        .values(38L, 1L, 25L, 1L, "BlogComment Text id = 38", "2017-10-31 17:50:40", "BT")
                        .values(39L, 1L, 26L, 2L, "BlogComment Text id = 39", "2017-10-31 18:20:50", "BT")
                        .values(40L, 1L, 32L, 1L, "BlogComment Text id = 40", "2017-10-31 18:25:30", "BT")
                        .values(41L, 1L, 33L, 2L, "BlogComment Text id = 41", "2017-10-31 19:00:50", "BT")
                        .values(42L, 1L, 31L, 1L, "BlogComment Text id = 42", "2017-10-31 19:20:30", "BT")
                        .values(43L, 1L, 34L, 2L, "BlogComment Text id = 43", "2017-10-31 19:30:50", "BT")
                        .values(44L, 1L, 37L, 1L, "BlogComment Text id = 44", "2017-10-31 19:45:20", "BT")
                        .build());
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

    public static void prepareBlogWithRootCommentBranchUpToOneLevelOfNesting(DataSource dataSource) {

        Operation operation;
        operation = sequenceOf(
                deleteAllFrom("blog_category_assignment", "comment", "blog"),
                insertInto("blog")
                        .columns("id", "title", "content", "user_id", "publication_date")
                        .values(1L, "Blog#1", "Content Text", 1L, "2017-10-31 16:40:31")
                        .build(),
                insertInto("comment")
                        .columns("id", "blog_id", "parent_comment_id", "user_id", "text", "comment_date", "comment_type")
                        .values(1L, 1L, null, 1L, "BlogComment Text id = 1", "2017-10-31 13:00:00", "BT")
                        .values(2L, 1L, 1L, 2L, "BlogComment Text id = 2", "2017-10-31 13:30:10", "BT")
                        .values(3L, 1L, 1L, 2L, "BlogComment Text id = 3", "2017-10-31 13:30:20", "BT")
                        .values(4L, 1L, 1L, 2L, "BlogComment Text id = 4", "2017-10-31 13:30:30", "BT")
                        .values(5L, 1L, 1L, 2L, "BlogComment Text id = 5", "2017-10-31 13:30:40", "BT")
                        .build());
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

    public static void prepareBlogWithRootCommentBranchUpToOneDoubleNesting(DataSource dataSource) {

        Operation operation;
        operation = sequenceOf(
                deleteAllFrom("blog_category_assignment", "comment", "blog"),
                insertInto("blog")
                        .columns("id", "title", "content", "user_id", "publication_date")
                        .values(1L, "Blog#1", "Content Text", 1L, "2017-10-31 16:40:31")
                        .build(),
                insertInto("comment")
                        .columns("id", "blog_id", "parent_comment_id", "user_id", "text", "comment_date", "comment_type")
                        .values(1L, 1L, null, 1L, "BlogComment Text id = 1", "2017-10-31 13:00:00", "BT")
                        .values(2L, 1L, 1L, 2L, "BlogComment Text id = 2", "2017-10-31 13:30:10", "BT")
                        .values(3L, 1L, 1L, 2L, "BlogComment Text id = 3", "2017-10-31 13:30:40", "BT")
                        .values(4L, 1L, 2L, 2L, "BlogComment Text id = 4", "2017-10-31 13:30:40", "BT")
                        .values(5L, 1L, 3L, 2L, "BlogComment Text id = 5", "2017-10-31 13:30:40", "BT")
                        .build());
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

}
