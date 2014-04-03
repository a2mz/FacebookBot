import java.net.MalformedURLException;
import java.net.URL;

import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.*;

///////////////////////////////////////////////////////////////////
//////                                                       //////
//////                                                       //////
//////                        REFERENCE:                     //////
//////       http://facebook4j.org/en/code-examples.html     //////
//////                                                       //////
///////////////////////////////////////////////////////////////////
public class FacebookBot {
	
	static Facebook facebook = new FacebookFactory().getInstance();
	static AccessToken OAuthAppAccessToken;
	
	public static void main(String[] args) throws FacebookException
	{
		Init();
		getNewsFeed();
	}
	
	static void Init()
	{
		facebook.setOAuthAppId("", ""); //(appId, appSecret
		facebook.setOAuthPermissions("public_profile, basic_info, read_stream, manage_friendlists, read_mailbox, read_page_mailboxes, publish_checkins, status_update, photo_upload, video_upload, sms, create_event, rsvp_event, email, xmpp_login, create_note, share_item, export_stream, publish_stream, ads_management, ads_read, read_insights, read_requests, manage_notifications, read_friendlists, manage_pages, publish_actions, user_birthday, user_religion_politics, user_relationships, user_relationship_details, user_hometown, user_location, user_likes, user_activities, user_interests, user_education_history, user_work_history, user_online_presence, user_website, user_groups, user_events, user_photos, user_videos, user_photo_video_tags, user_notes, user_checkins, user_questions, user_friends, user_about_me, user_status, user_games_activity, user_subscriptions, friends_birthday, friends_religion_politics, friends_relationships, friends_relationship_details, friends_hometown, friends_location, friends_likes, friends_activities, friends_interests, friends_education_history, friends_work_history, friends_online_presence, friends_website, friends_groups, friends_events, friends_photos, friends_videos, friends_photo_video_tags, friends_notes, friends_checkins, friends_questions, friends_about_me, friends_status, friends_games_activity, friends_subscriptions, user_actions.books, user_actions.music, user_actions.video, user_actions.news, friends_actions.books, friends_actions.music, friends_actions.video, friends_actions.news"); //commaSeparetedPermissions
		facebook.setOAuthAccessToken(new AccessToken("", null)); //accessToken
	}
	
	static void post(String text) throws FacebookException
	{
		facebook.postStatusMessage(text);
	}
	
	static void postLink(String URL, String pictureURL, String name, String caption, String description) throws MalformedURLException, FacebookException
	{
		PostUpdate post = new PostUpdate(new URL(URL))
        .picture(new URL(pictureURL))
        .name(name)
        .caption(caption)
        .description(description);
		facebook.postFeed(post);
	}
	
	static void getNewsFeed() throws FacebookException
	{
		ResponseList<Post> feed = facebook.getHome();
		
		for(Post s : feed)
		{
			System.out.println("----------------------------------------------------------------");
			if (s.getFrom()!=null ) {
				System.out.println(s.getFrom().getName());
			}
			if (s.getName()!=null ) {
			System.out.println(s.getName());
			}
			if (s.getMessage()!=null ) {
				System.out.println(s.getMessage());
			}
			System.out.println("----------------------------------------------------------------\n\n");
		}
	}
	
	static void comment(String text)
	{
		//facebook.commentPost(PostId, text)
	}
	
	static void ExecuteFQL() throws FacebookException, JSONException //Sample

	{
		String query = "SELECT uid2 FROM friend WHERE uid1=me()";
		JSONArray jsonArray = facebook.executeFQL(query);
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject jsonObject = jsonArray.getJSONObject(i);
		    System.out.println(jsonObject.get("uid2"));
		}
	}
	
	static void getOAuth() throws FacebookException
	{
		OAuthAppAccessToken = facebook.getOAuthAppAccessToken();
		
		ResponseList<Account> accounts = facebook.getAccounts();
		Account yourPageAccount = accounts.get(0);  // if index 0 is your page account.
		String pageAccessToken = yourPageAccount.getAccessToken();
	}

}
