/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.facebook.tool.controller;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import com.restfb.types.User;
import static java.lang.System.out;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldClass{
    public static String getFeedPosts(FacebookClient facebookClient, String feedId, long num_posts)
    {
        StringBuilder posts = new StringBuilder();
        Connection<Post> myFeed = facebookClient.fetchConnection(feedId + "/feed", Post.class);
        int count = 0;
        for (List<Post> myFeedPage : myFeed) {
            for (Post post : myFeedPage) {
                if(count == num_posts) return posts.toString();
                count++;
                posts.append(getPostsDetails(post));
                //facebookClient.publish(post.getId()+"/likes", Boolean.class);
            }
        }
        return posts.toString();
    }
    public static String getHomePosts(FacebookClient facebookClient, String feedId, long num_posts)
    {
        StringBuilder posts = new StringBuilder();
        Connection<Post> myFeed = facebookClient.fetchConnection(feedId + "/home", Post.class);
        int count = 0;
        for (List<Post> myFeedPage : myFeed) {
            for (Post post : myFeedPage) {
                if(count == num_posts) return posts.toString();
                count++;
                posts.append(getPostsDetails(post));
                //facebookClient.publish(post.getId()+"/likes", Boolean.class);
            }
        }
        return posts.toString();
    }
    public static String likeFeedPosts(FacebookClient facebookClient, String feedId, long num_posts)
    {
        StringBuilder posts = new StringBuilder();
        Connection<Post> myFeed = facebookClient.fetchConnection(feedId + "/feed", Post.class);
        int count = 0;
        for (List<Post> myFeedPage : myFeed) {
            for (Post post : myFeedPage) {
                if(count == num_posts) return posts.toString();
                count++;
                posts.append(getPostsDetails(post));
                try{
                    facebookClient.publish(post.getId()+"/likes", Boolean.class);
                }
                catch(Exception ex){
                    continue;
                }
            }
        }
        return posts.toString();
    }
    public static String getPostsDetails(Post post){
        StringBuilder details = new StringBuilder("\n\n-----------------------------------------------------------");
        details.append("\n|Post Id: " + post.getId());
        details.append("\n|Ower: " + post.getFrom().getId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        details.append("\n|Caption: " + post.getCaption());
        details.append("\n|Time: " + sdf.format(post.getCreatedTime()));
        details.append("\n|Message: " + post.getMessage());
        details.append("\n|Likes: " + post.getLikesCount() + "\tComments: " + post.getCommentsCount());
        details.append("\n-----------------------------------------------------------");
        return details.toString();
    }
    public static String getUserInfo(User user){
        StringBuilder builder = new StringBuilder("User: " + user.getId());
        builder.append("\nUsername: " + user.getName());
        builder.append("\nDate of birth: " + user.getBirthday());
        builder.append("\nEmail: " + user.getEmail());
        return builder.toString();
    }
    public static String getUserInfo(FacebookClient facebookClient){
        User user = facebookClient.fetchObject("me", User.class);
        StringBuilder builder = new StringBuilder("User: " + user.getId());
        builder.append("\nUsername: " + user.getName());
        builder.append("\nDate of birth: " + user.getBirthday());
        builder.append("\nEmail: " + user.getEmail());
        return builder.toString();
    }
    public static FacebookClient getFacebookClient(String token){
        return new  DefaultFacebookClient(token);
    }
    public static HashMap<String,Integer>  getCommentPhones(FacebookClient facebookClient ,String postId){
        HashMap<String,Integer> phones = new HashMap<String, Integer>();
        Connection<Comment> commentConnection  = facebookClient.fetchConnection(postId + "/comments", 
                Comment.class, Parameter.with("limit", 10));
        int personalLimit = 50;

        for (List<Comment> commentPage : commentConnection) {
          for (Comment comment : commentPage) {
              
              {
                  phones.put(comment.getFrom().getName(), Integer.parseInt(getNumbers(comment.getMessage())));
              }
            personalLimit--;
            // break both loops
            if (personalLimit == 0) {
               return phones;
            }
          }
        }
        return phones;
    }
    public static String getNumbers(String string){
        String numbers = "0";
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(string);
        while (m.find()) {
            numbers = m.group();
        }
        return numbers;
    }
    private static void searchOnHome(FacebookClient facebookClient){
        Connection<User> targetedSearch = facebookClient.fetchConnection("search", User.class,
        Parameter.with("q", "Nha"), Parameter.with("type", "user"));
        List<User> users = targetedSearch.getData();
        for(int i = 0; i < users.size(); i ++){
            out.println(getUserInfo(users.get(i)));
        }
    }
    public static void getComments(FacebookClient facebookClient ,String postId){
        Connection<Comment> commentConnection  = facebookClient.fetchConnection(postId + "/comments", 
                Comment.class, Parameter.with("limit", 10));
        int personalLimit = 50;

        for (List<Comment> commentPage : commentConnection) {
          for (Comment comment : commentPage) {
            out.println(comment.getFrom().getName() + ": " + comment.getMessage());
            personalLimit--;
            // break both loops
            if (personalLimit == 0) {
               return;
            }
          }
        }
    }

    public static String commentFeedPosts(FacebookClient facebookClient, String feedId, long num_posts, String comment)
    {
        StringBuilder posts = new StringBuilder();
        Connection<Post> myFeed = facebookClient.fetchConnection(feedId + "/feed", Post.class);
        int count = 0;
        for (List<Post> myFeedPage : myFeed) {
            for (Post post : myFeedPage) {
                if(count == num_posts) return posts.toString();
                try{
                    posts.append(getPostsDetails(post));
                    facebookClient.publish(post.getId()+"/comments", String.class, Parameter.with("message", comment));
                    count++;
                } catch(Exception ex){
                    continue;
                }
                
            }
        }
        return posts.toString();
    }
    public static String commentFeedPosts(FacebookClient facebookClient, String feedId, long num_posts, String comment, String fromId)
    {
        StringBuilder posts = new StringBuilder();
        Connection<Post> myFeed = facebookClient.fetchConnection(feedId + "/feed", Post.class);
        int count = 0;
        for (List<Post> myFeedPage : myFeed) {
            for (Post post : myFeedPage) {
                if(count == num_posts) return posts.toString();
                count++;
                try{
                   if(post.getFrom().getId().equals(fromId)){
                    posts.append(getPostsDetails(post));
                    facebookClient.publish(post.getId()+"/comments", String.class, Parameter.with("message", comment));
                    } 
                } catch(Exception ex){
                    continue;
                }
                
            }
        }
        return posts.toString();
    }
}
