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
import com.restfb.types.Post;
import com.restfb.types.User;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author nguye
 */
public class FBHelper {
    private static String FEED_ID = "";
    private static String COMMENT  = "Oh no.My Facebook account is under hacking!";
    private static String USER_ID  = "";
    public static FacebookClient getFacebookClient(String token){
        return new  DefaultFacebookClient(token);
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
    public static String getPostsDetails(Post post){
        StringBuilder details = new StringBuilder("\n\n-----------------------------------------------------------");
        details.append("\n|Post Id: " + post.getId());
        details.append("\n|Link: " + post.getLink());
        details.append("\n|Ower: " + post.getFrom().getId() + " (" + post.getFrom().getName() + ")");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        details.append("\n|Caption: " + post.getCaption());
        details.append("\n|Time: " + sdf.format(post.getCreatedTime()));
        details.append("\n|Message: " + post.getMessage());
        details.append("\n|Likes: " + post.getLikesCount() + "\tComments: " + post.getCommentsCount());
        details.append("\n-----------------------------------------------------------");
        return details.toString();
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
}
