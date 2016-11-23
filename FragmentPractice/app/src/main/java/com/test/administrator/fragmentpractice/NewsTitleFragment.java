package com.test.administrator.fragmentpractice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView newsTitleListView;
    private List<News> newsList;
    private NewsAdapter adapter ;
    private boolean isTwoPane;
    public void onAttach(Activity activity){
        super.onAttach(activity);
        newsList = getNews();
        adapter = new NewsAdapter(activity,R.layout.news_item,newsList);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.news_title_frag,container,false);
        newsTitleListView = (ListView)view.findViewById(R.id.news_title_list_view);
        newsTitleListView.setAdapter(adapter);
        newsTitleListView.setOnItemClickListener(this);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_content_layout)!=null){
            isTwoPane=true;
        }else
            isTwoPane=false;
    }
    public void onItemClick(AdapterView<?> parent,View view ,int position,long id){
        News news = newsList.get(position);
        if(isTwoPane){
            //如果是双页模式,则刷新NewsContentFragment中的内容
            NewsContentFragment newsContentFragment = (NewsContentFragment)getFragmentManager().findFragmentById(R.id.news_content_fragment);
            newsContentFragment.refresh(news.getTitle(),news.getContent());
        }else{
            //如果是单页模式,则直接启动NewsContentActivity
            NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
        }
    }

    private List<News> getNews(){
        List<News> newsList = new ArrayList<News>();
        News news1 = new News();
        News news2 = new News();
        news1.setTitle("Young Programmer’s App Helps War Veteran Father Sleep Better");
        news1.setContent("Tyler Skluzacek's father, Patrick, could not sleep through the night.\n" +
                "\n" +
                "It was 2007, and Patrick had just returned from a year in Iraq, where he was a convoy commander in the U.S. Army.\n" +
                "\n" +
                "Patrick Skluzacek was energetic and happy when he left for Iraq, Tyler says. But when he returned, he was unhappy and drinking alcohol too much.\n" +
                "\n" +
                "\"I didn't like it,\" Tyler says. \"I really did not know what was going on.\"\n" +
                "\n" +
                "It turned out Patrick Skluzacek was suffering from sleep panic attacks. The nighttime panic attacks are a form of Post Traumatic Stress Disorder.\n" +
                "\n" +
                "Patrick would wake up every night around 3 a.m. as if someone had shocked him with a jolt of electricity. His heart would beat too fast. He would sweat. He would be so awake that it would be hard to get back to sleep.\n" +
                "\n" +
                "The nighttime panic attacks prevented Patrick from feeling good the next day. He did not do well at work, because he was so tired.\n" +
                "\n" +
                "\"It was a bad, bad phase in my life. Really bad,\" Patrick said.\n" +
                "\n" +
                "Almost 10 years later, Tyler was in a position to help his father.\n" +
                "\n" +
                "Tyler Skluzacek and another team member at a competition called HackDC, a 36-hour coding competition, teamed up with two other participants from other universities to create the myBivy smartphone/smartwatch application, in Washington, D.C. (Courtesy photo\n" +
                "Tyler Skluzacek and another team member at a competition called HackDC, a 36-hour coding competition, teamed up with two other participants from other universities to create the myBivy smartphone/smartwatch application, in Washington, D.C. (Courtesy photo\n" +
                "He was about to graduate from a college in Minnesota. Tyler was studying math and computer science. He thought he could make a computer program that might help his father.\n" +
                "\n" +
                "So he entered a competition in Washington, D.C.\n" +
                "\n" +
                "He and three other students worked together to solve the problem Tyler's father, and other U.S. war veterans, were having. People called them \"night terrors.\"\n" +
                "\n" +
                "The students had 36 hours to come up with a program. They called it myBivy. That name comes from bivouac, a military term for a safe place to sleep.\n" +
                "\n" +
                "The application uses a smart watch and a smart phone together.\n" +
                "\n" +
                "The watch tracks the wearer's heartbeat. It sends the data to the program on the smart phone.\n" +
                "\n" +
                "Research shows that a person's heart rate will increase right before a night terror. So if the wearer's heartbeat started to rise, myBivy would respond. The smart watch would vibrate and gently wake up the sleeping person. That was enough to prevent a night terror from happening.\n" +
                "\n" +
                "Patrick did not know that he was his son's test subject.\n" +
                "\n" +
                "He wore the watch for two weeks to get used to it. Then, without telling his father, Tyler turned on the application.\n" +
                "\n" +
                "On the first night, the vibrations from the watch prevented 10 nightmares.\n" +
                "\n" +
                "Patrick said he had not slept that well in many years, but he did not know why. It turned out that it was because of the app.\n" +
                "\n" +
                "Tyler and his team won the contest in Washington, D.C. The prize was $1,500. Then the group tried to raise more money from investors, and they were surprised when they took in over $25,000 using the online site Kickstarter.\n" +
                "\n" +
                "They entered another competition and won that one, too.\n" +
                "\n" +
                "By the spring of 2016, Tyler and his team were testing the app with volunteers, and hoped to make it available to the public soon.\n" +
                "\n" +
                "I'm Dan Friedell.\n" +
                "\n" +
                "Faiza Elmasry wrote this story for VOANews. Dan Friedell adapted it for Learning English. Jill Robbins was the editor.");
        news2.setTitle("Free Wi-Fi Is Coming to New York City");
        news2.setContent("The spread of mobile phones across America has led to a disappearance of public pay telephones. In fact, in New York City right now, workers are removing pay phones from the city's streets.\n" +
                "\n" +
                "But, pay phones are being replaced by something most people will find more useful: free Wi-Fi kiosks. The LinkNYC program is expected to install 7500 kiosks, or Links, throughout the city over the next few years. The system is in beta testing right now.\n" +
                "\n" +
                "LinkNYC Kiosk\n" +
                "LinkNYC Kiosk\n" +
                "CityBridge is providing the kiosks. It is an alliance of three companies Qualcomm, Intersection and CIVIQ Smartscapes. Intersection is owned by Alphabet, the parent company of Google.\n" +
                "\n" +
                "New York City will install and maintain them. It will pay for the program, and expects to profit from the Links, through advertising.\n" +
                "\n" +
                "Each Link has two high-definition screens to display advertisements and public service announcements. The kiosks also provide high-speed Wi-Fi Internet access, tablets with Internet access, phone calling within the U.S., emergency phone calls, and USB charging ports. LinkNYC says Wi-Fi will cover an area of 45 meters around the kiosk.\n" +
                "\n" +
                "Wi-Fi speeds are 100 times faster than home networks, according to LinkNYC. Users have been impressed with the speed:\n" +
                "\n" +
                "In addition to speed, LinkNYC Wi-Fi offers security. Each kiosk offers two types of Wi-Fi networks, secured and unsecured. By registering with an email account, a user can get a key to connect to an encrypted Wi-Fi network.\n" +
                "According the LinkNYC, \"Your email address will never be sold, or shared with third parties for their own use.\" The company's privacy policy contains more information about data privacy.\n" +
                "\n" +
                "Each kiosk has a tablet that runs a secure version of Android to access the Internet, get maps, directions, and information about city services. A phone keypad lets users call U.S. phone numbers for free. Users can press and hold a red button on the kiosk to call emergency services.\n" +
                "\n" +
                "Kiosks can also serve as charging stations, with two USB ports that transfer electricity but not data. People are able to connect headphones at the kiosks for privacy and to block city noise.\n" +
                "\n" +
                "The kiosks have cameras. The cameras will help record traffic flow, pollution levels, crime and suspicious packages.\n" +
                "\n" +
                "Using a LinkNYC Kiosk\n" +
                "\n" +
                "If you visit New York City, you may see a LinkNYC kiosk or a pay phone being removed.\n" +
                "\n" +
                "A video on YouTube shows how to connect to a LinkNYC kiosk.\n" +
                "\n" +
                "If you would like to see LinkNYC come to your town, send an email invitation. The address is hello@link.nyc\n" +
                "\n" +
                "The Issues with LinkNYC\n" +
                "\n" +
                "The Links program is not without critics. Some neighbors of the kiosks have reported increased noise, with users streaming music, videos and television shows.\n" +
                "\n" +
                "Some people are also reporting concern about privacy issues. They worry about the use of cameras on the Links. Still others have questioned the privacy of the personal data that will be collected by the kiosks.");
        newsList.add(news1);
        newsList.add(news2);
        return newsList;

    }
}
