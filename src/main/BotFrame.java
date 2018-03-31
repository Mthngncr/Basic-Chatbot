package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.mongodb.Mongo;

import mongodb.MongoInstance;
import products.CellPhone;
import products.Television;
import redisio.RedisClient;

public class BotFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtEnter;
	private JTextArea txtChat;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BotFrame frame = new BotFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	ArrayList<TwitterObject> products;
	SenticNet senticNet;
	RedisClient redisInstance = RedisClient.getInstance();
	MongoInstance mongoInstance=MongoInstance.getInstance();

	public BotFrame() {

		try {
			products = new ArrayList<>();
			senticNet = new SenticNet();
		} catch (ParserConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SAXException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		InitializeFrame();

		final ArrayList<String> exceptionMessageList = new ArrayList<String>(
				Arrays.asList("maalesef anlasilmadi...", "lutfen tekrarlar misin", "???"));
		final ArrayList<String> greetingMessageList = new ArrayList<String>(
				Arrays.asList("ooo selammm..", "merhabalar", "mrb"));

		final ArrayList<String> howareyouMessageList = new ArrayList<String>(
				Arrays.asList("Tesekkurler,iyiyim siz?", "Ben iyiyim ya siz ?"));

		final ArrayList<String> goodByeMessageList = new ArrayList<String>(
				Arrays.asList("İyi Günler Dilerim!", "Hoscakalin.."));

		final ArrayList<CellPhone> cellPhoneList = new ArrayList<CellPhone>();
		final ArrayList<Television> televisionList = new ArrayList<Television>();

		cellPhoneList.add(new CellPhone(1, "Apple", "Iphone X", 64, "3GB"));
		cellPhoneList.add(new CellPhone(2, "Apple", "Iphone 7", 64, "2GB"));
		cellPhoneList.add(new CellPhone(3, "Huawei", "Huawei P10", 64, "4GB"));
		televisionList.add(new Television(1, "Samsung", "TV", 109, "3840 x 2160"));
		televisionList.add(new Television(2, "LG", "TV", 178, "3840 x 2160"));
		
		for(CellPhone cp:cellPhoneList) {
			mongoInstance.InsertDocument(cp);
		}
		for(Television tv:televisionList) {
			mongoInstance.InsertDocument(tv);
		}

		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uText = txtEnter.getText();

				txtChat.append("You: " + uText + "\n");
				txtEnter.setText("");
				try {
					AIResponse(exceptionMessageList, greetingMessageList, howareyouMessageList, goodByeMessageList,
							cellPhoneList, televisionList, uText);
				} catch (IOException | ParserConfigurationException | SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		sendButton.setBounds(523, 320, 97, 40);
		contentPane.add(sendButton);

		txtEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uText = txtEnter.getText();

				txtChat.append("You: " + uText + "\n");
				txtEnter.setText("");

				try {
					AIResponse(exceptionMessageList, greetingMessageList, howareyouMessageList, goodByeMessageList,
							cellPhoneList, televisionList, uText);
				} catch (IOException | ParserConfigurationException | SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
	}

	private void AIResponse(final ArrayList<String> exceptionMessageList, final ArrayList<String> greetingMessageList,
			final ArrayList<String> howareyouMessageList, final ArrayList<String> goodByeMessageList,
			final ArrayList<CellPhone> cellPhoneList, final ArrayList<Television> televisionList, String uText)
			throws MalformedURLException, IOException, ParserConfigurationException, SAXException

	{

		if (uText.contains("selam")) {
			// txtChat.append("AI:" + "ooo selamlar" + "\n");
			decideRandom(greetingMessageList);

		} else if (uText.contains("nasilsin") || uText.contains("nasilsiniz")) {

			decideRandom(howareyouMessageList);

		} else if (uText.contains("iyi")) {
			txtChat.append("AI: " + "Guzel Haber!!" + "\n");
			txtChat.append("AI: " + "Size nasil yardimci olabilirim?:" + "\n");
		} else if (uText.contains("urun sececegim")) {
			// urun secimi
			txtChat.append("AI: " + "Lutfen urunu seciniz:" + "\n");
			txtChat.append("AI: " + "1: Cep Telefonu" + "\n");
			txtChat.append("AI: " + "2: Televizyon" + "\n");

		} else if (uText.equals("1")) {

			txtChat.append("AI: " + "Cep Telefonu secildi..." + "\n");
			txtChat.append("AI: " + "Listedeki Telefonlar:" + "\n");
			for (CellPhone cellP : cellPhoneList) {

				TwitterObject twitter = new TwitterObject(cellP.getpModel());
				lookTweetsinSentic(twitter);

				/*
				 * txtChat.append("AI: " + cellP.getpModel() + " Sentic Record : " +
				 * String.format("%.3f", twitter.getAvg_intensity()) + "\n");
				 */
			}

			Collections.sort(products);
			for (TwitterObject i : products) {

				txtChat.append(i.toString());
			}
			System.out.println(products.toString());
			products.clear();

		} else if (uText.equals("2")) {

			txtChat.append("AI: " + "Televizyon secildi..." + "\n");
			txtChat.append("AI: " + "Listedeki Televizyonlar:" + "\n");
			for (Television tv : televisionList) {

				TwitterObject twitter = new TwitterObject(tv.getpBrand() + " " + tv.getpModel());
				lookTweetsinSentic(twitter);
				/*
				 * txtChat.append("AI: " + tv.getpBrand() + " " + tv.getpModel() +
				 * " Sentic Record : " + String.format("%.3f", twitter.getAvg_intensity()) +
				 * "\n");
				 */
			}

			Collections.sort(products);
			for (TwitterObject i : products) {

				txtChat.append(i.toString());
			}
			System.out.println(products.toString());
			products.clear();

		} else if (uText.contains("bye") || uText.contains("gule gule")) {
			decideRandom(goodByeMessageList);
		} else {
			decideRandom(exceptionMessageList);
		}
	}

	private void lookTweetsinSentic(TwitterObject twitter) {
		

		twitter.SearhTweets(10);
		HashMap<String, Double> tweetList = twitter.GetTweetList();

		for (Map.Entry<String, Double> tweet : tweetList.entrySet()) {

			String[] splittedTweet = tweet.getKey().split(" ");
			for (String word : splittedTweet) {

				tweet.setValue(tweet.getValue() + senticNet.RequestSenticNet(word));

			}
			redisInstance.setRedis(tweet.getKey(), tweet.getValue());
			System.out.println(redisInstance.getRedis(tweet.getKey()));
			twitter.setTotal_intensity(twitter.getTotal_intensity() + tweet.getValue());
		}

		twitter.setAvg_intensity(twitter.getTotal_intensity() / tweetList.size());

		products.add(twitter);

		// System.out.println("Product name = " + twitter.getProductName() + " Average
		// Intensity= " + twitter.getAvg_intensity());
	}

	private void InitializeFrame() {
		setTitle("Chatbot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtEnter = new JTextField();
		txtEnter.setBounds(12, 320, 499, 40);
		contentPane.add(txtEnter);
		txtEnter.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 608, 295);
		contentPane.add(scrollPane);

		txtChat = new JTextArea();
		txtChat.setFont(new Font("Monospaced", Font.PLAIN, 16));
		scrollPane.setViewportView(txtChat);
		txtChat.setEditable(false);
	}

	private void decideRandom(ArrayList<String> messageList) {
		int decider = (int) (Math.random() * messageList.size());
		txtChat.append("AI: " + messageList.get(decider) + "\n");
	}
}


	