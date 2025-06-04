package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleServer extends AbstractServer {
	private static final SessionFactory sessionFactory = getSessionFactory();

	public SimpleServer(int port) throws Exception {
		super(port);
		initializeDatabase(); System.out.println("Server initialized");
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();

		Scanner input = new Scanner(System.in);

		System.out.println("MySQL Database Name: FinalDatabase");

		System.out.print("Please enter your password: ");
		String password = input.next();

		input.close();

		configuration.setProperty("hibernate.connection.password", password);
		configuration.addAnnotatedClass(Movie.class);
		configuration.addAnnotatedClass(SoonMovie.class);
		configuration.addAnnotatedClass(HomeMovie.class);
		configuration.addAnnotatedClass(Branch.class);
		configuration.addAnnotatedClass(Hall.class);
		configuration.addAnnotatedClass(Screening.class);
		configuration.addAnnotatedClass(HeadManager.class);
		configuration.addAnnotatedClass(BranchManager.class);
		configuration.addAnnotatedClass(ContentManager.class);
		configuration.addAnnotatedClass(CustomerServiceWorker.class);
		configuration.addAnnotatedClass(Complaint.class);
		configuration.addAnnotatedClass(ChangePriceRequest.class);
		configuration.addAnnotatedClass(Cinema.class);
		configuration.addAnnotatedClass(Customer.class);
		configuration.addAnnotatedClass(Purchase.class);
		configuration.addAnnotatedClass(HomeMoviePurchase.class);
		configuration.addAnnotatedClass(Card.class);
		configuration.addAnnotatedClass(Notification.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	private static void initializeDatabase() throws Exception {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			generateMovies(session);
			generateSoonMovies(session);
			generateHomeMovies(session);
			generateBranches(session);
			generateHalls(session);
			generateScreenings(session);
			generateHeadManager(session);
			generateBranchManager(session);
			generateContentManager(session);
			generateCustomerServiceWorker(session);
			generateCinema(session);
			generateComplaints(session);
			generateChangePriceRequest(session);
			generateCustomersAndPurchases(session);
			generateHomeMoviePurchases(session);
			generateCards(session);
			generateNotifications(session);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generateCinema(Session session) throws Exception {
		Cinema cinema = new Cinema(100, 90, 1000);
		Cinema.cinema = cinema;
		session.save(cinema);
		session.flush();
	}

	private static void generateComplaints(Session session) throws Exception {
		Complaint complaint1 = new Complaint("Dema Omar", 123123123L, "1234567890",
				"Purchases", "john@example.com", "Overcharged for my last order.", LocalDateTime.of(2024, 9, 25, 12, 30),
				"Thank you for reaching out,\nyou will be refunded accordingly.", false);
		Complaint complaint2 = new Complaint("Shada Ghanem", 123456789L, "0987654321",
				"Movie link", "shada@example.com", "Unable to access the link.", LocalDateTime.of(2024, 9, 25, 13, 30),
				"Thank you for reaching out,\nthe link will be activated at the screening's scheduled time.", false);
		Complaint complaint3 = new Complaint("Alice Johnson", 555666777L, "1122334455",
				"Other", "alice@example.com", "Loved the new movie selection!", LocalDateTime.of(2024, 9, 9, 11, 32),
				"Thank you for reaching out,\nnew movies are coming out soon!", true);
		Complaint complaint4 = new Complaint("Jane Smith", 987654321L, "9988776655",
				"Screening", "bob@example.com", "When will the new movie be released?", LocalDateTime.of(2024, 9, 5, 22, 4),
				"Thank you for reaching out,\nthe release date for the new movie has not yet been announced.\nPlease stay tuned for future updates.", true);

		Complaint complaint5 = new Complaint("customer", 111111111L, "1111111111", "Haifa Cinema",
				"a@gmail.com", "complaint " , LocalDateTime.of(2024, 9, 11, 20, 30),
				"answered", true
		);
		Complaint complaint6 = new Complaint("customer", 111111111L, "1111111111", "Eilat Cinema",
				"a@gmail.com", "complaint " , LocalDateTime.of(2024, 5, 8, 20, 30),
				"answered", true
		);
		Complaint complaint7 = new Complaint("customer", 111111111L, "1111111111", "Karmiel Cinema",
				"a@gmail.com", "complaint " , LocalDateTime.of(2024, 7, 11, 20, 30),
				"answered", true
		);
		Complaint complaint8 = new Complaint("customer", 111111111L, "1111111111", "Tel Aviv Cinema",
				"a@gmail.com", "complaint " , LocalDateTime.of(2024, 8, 19, 20, 30),
				"answered", true
		);
		Complaint complaint9 = new Complaint("customer", 111111111L, "1111111111", "Haifa Cinema",
				"a@gmail.com", "complaint " , LocalDateTime.of(2024, 6, 3, 20, 30),
				"answered", true
		);
		Complaint complaint10 = new Complaint("customer", 111111111L, "1111111111", "Jerusalem Cinema",
				"a@gmail.com", "complaint " , LocalDateTime.of(2024, 6, 30, 20, 30),
				"answered", true
		);
		Complaint complaint11 = new Complaint("customer", 111111111L, "1111111111", "Jerusalem Cinema",
				"a@gmail.com", "complaint " , LocalDateTime.of(2024, 8, 15, 20, 30),
				"answered", true
		);
		Complaint complaint12 = new Complaint("customer", 111111111L, "1111111111", "Tel Aviv Cinema",
				"a@gmail.com", "complaint " , LocalDateTime.of(2024, 8, 15, 20, 30),
				"answered", true
		);
		Complaint complaint13 = new Complaint("customer", 111111111L, "1111111111", "Haifa Cinema",
				"a@gmail.com", "complaint " , LocalDateTime.of(2024, 8, 17, 20, 30),
				"answered", true
		);

		session.save(complaint1);
		session.save(complaint2);
		session.save(complaint3);
		session.save(complaint4);
		session.save(complaint5);
		session.save(complaint6);
		session.save(complaint7);
		session.save(complaint8);
		session.save(complaint9);
		session.save(complaint10);
		session.save(complaint11);
		session.save(complaint12);
		session.save(complaint13);
		session.flush();
	}

	private static void generateCustomersAndPurchases(Session session) {
		List<Purchase> purchases1 = new ArrayList<>();
		List<Purchase> purchases2 = new ArrayList<>();
		List<Purchase> purchases6 = new ArrayList<>();

		Customer customer1 = new Customer(123123123, "Dima om", "dima.oma@example.com", "0501234567", purchases1, false);
		Customer customer2 = new Customer(123456789, "Shada gh", "shada.gha@example.com", "0527654321", purchases2, false);
		Customer customer3 = new Customer(111111111, "Rozaline", "roz@example.com", "0549195105", purchases6, false);
		Customer customer4 = new Customer(444555666, "Dana m", "dana@example.com", "0540095105", purchases6, false);
		//Create purchases and add them to customers
		Purchase purchase1 = new Purchase("Movie Ticket",LocalDateTime.of(2021,9,15,12,40), "Credit Card", 200.00, customer3,"Haifa Cinema",2,"Movie Ticket Details:\n" + "Movie: Inside out\n" + "Date: 2024-10-01\n" + "Time: 17:00\n" + "Seat ID: E2\n" + "Branch: Haifa Cinema");
		Purchase purchase2 = new Purchase("Movie Card",  LocalDateTime.of(2024,9,18,10,15), "Cash", 800, customer1,"telAvivCinema",1,"A cinema card was ordered containing 20 tickets, which allows access to movie screenings at all our branches based on available seating.");
		Purchase purchase3 = new Purchase("Movie Link",  LocalDateTime.of(2024,9,23,21,10),"Credit Card", 120, customer2,"Movie link was ordered for the movie: Wire Room. Viewing is limited to the screening time you selected: 2024-09-26 15:00");
		Purchase purchase4 = new Purchase("Movie Card", LocalDateTime.of(2024, 9, 11, 20, 30), "Credit Card", 1000, customer1, null, 2, "2 cinema cards were ordered containing 20 tickets each, which allows access to movie screenings at all our branches based on available seating.");
		Purchase purchase5 = new Purchase("Movie Card", LocalDateTime.of(2024, 8, 8, 11, 11), "Credit Card", 1500, customer2, null, 1, "A cinema card was ordered containing 20 tickets, which allows access to movie screenings at all our branches based on available seating.");

		Purchase purchase6 = new Purchase("Movie Ticket", LocalDateTime.of(2024, 9, 8, 11, 11), "Credit Card", 1500, customer3, "Haifa Cinema", 18, "Movie Ticket Details:\n" + "Movie: Inside out\n" + "Date: 2024-10-01\n" + "Time: 17:00\n" + "Seat ID: E2\n" + "Branch: Haifa Cinema");
		Purchase purchase8 = new Purchase("Movie Ticket",LocalDateTime.of(2021,7,15,12,40), "Credit Card", 1200.00, customer4,"Haifa Cinema",2,"Movie Ticket Details:\n" + "Movie: Inside out\n" + "Date: 2024-10-01\n" + "Time: 17:00\n" + "Seat ID: E2\n" + "Branch: Haifa Cinema");
		Purchase purchase9 = new Purchase("Movie Ticket",LocalDateTime.of(2021,7,15,12,40), "Credit Card", 1200.00, customer4,"Haifa Cinema",2,"Movie Ticket Details:\n" + "Movie: Inside out\n" + "Date: 2024-10-01\n" + "Time: 17:00\n" + "Seat ID: E2\n" + "Branch: Haifa Cinema");
		Purchase purchase10 = new Purchase("Movie Ticket",LocalDateTime.of(2021,7,15,12,40), "Credit Card", 1200.00, customer3,"Haifa Cinema",2,"Movie Ticket Details:\n" + "Movie: Inside out\n" + "Date: 2024-10-01\n" + "Time: 17:00\n" + "Seat ID: E2\n" + "Branch: Haifa Cinema");
		Purchase purchase11 = new Purchase("Movie Ticket",LocalDateTime.of(2021,7,15,12,40), "Credit Card", 1200.00, customer3,"Haifa Cinema",2,"Movie Ticket Details:\n" + "Movie: Inside out\n" + "Date: 2024-10-01\n" + "Time: 17:00\n" + "Seat ID: E2\n" + "Branch: Haifa Cinema");


		//Adding purchases to the list
		//customer1.getPurchaseHistory().add(purchase1);
		customer1.getPurchaseHistory().add(purchase2);
		customer2.getPurchaseHistory().add(purchase3);
		customer1.getPurchaseHistory().add(purchase4);
		customer2.getPurchaseHistory().add(purchase5);

		customer3.getPurchaseHistory().add(purchase6);
		customer3.getPurchaseHistory().add(purchase10);
		customer3.getPurchaseHistory().add(purchase11);

		customer4.getPurchaseHistory().add(purchase8);
		customer4.getPurchaseHistory().add(purchase9);

		//Saving customers
		session.save(customer1);
		session.save(customer2);
		session.save(customer3);
		session.save(customer4);
		session.flush();

	}

	private static void generateCards(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		Customer customer1 = session.get(Customer.class, 111111111);
		Customer customer2 = session.get(Customer.class, 444555666);
		Customer customer3 = session.get(Customer.class, 123123123);
		Customer customer4 = session.get(Customer.class, 123456789);
		Card card1 = new Card("Card", LocalDateTime.of(2024, 9, 11, 20, 30), "Credit Card"
				, 1000, customer3, null, 1, "A cinema card containing 20 tickets.", 15, "Regular");
		session.save(card1);
		Card card3 = new Card("Card", LocalDateTime.of(2024, 9, 11, 20, 30), "Credit Card"
				, 1000, customer3, null, 1, "A cinema card containing 20 tickets.", 13, "Regular");
		session.save(card3);
		Card card2 = new Card("Card", LocalDateTime.of(2024, 8, 8, 11, 11), "Credit Card"
				, 1500, customer4, null,1, "A cinema card containing 20 tickets.",5, "VIP");
		session.save(card2);
		Card card4 = new Card("Card", LocalDateTime.of(2024, 9, 18, 10, 15), "Credit Card"
				, 1500, customer3, null,1, "A cinema card containing 20 tickets.",4, "VIP");
		session.save(card4);


		int numberOfCards = 30;  // Total number of card purchases
		int cardsPerDay = 2;  // Number of card purchases per day

		double price = 1000;  // Fixed price for all cards
		String paymentMethod = "Credit Card";
		String cardDescription = "A cinema card containing 20 tickets.";  // Same description for all cards
		int tickets = 20;  // Number of tickets for each card
		String cardType = "Regular";  // Fixed card type

		// Loop to generate multiple card purchases
		for (int i = 0; i < numberOfCards; i++) {
			// Calculate the day and time for each card purchase
			int dayOfMonth = (i / cardsPerDay) + 1;  // Ensure every day gets `cardsPerDay` purchases
			int hourOffset = (i % cardsPerDay) * 3;  // Spread out purchases within the same day by 3 hours

			LocalDateTime purchaseDate = LocalDateTime.of(2024, 9, dayOfMonth, 12 + hourOffset, 0);  // Purchases at different hours each day (12:00, 15:00, etc.)

			// Alternate between customers for each card purchase
			Customer currentCustomer = (i % 2 == 0) ? customer1 : customer2;

			// Create new Card for each iteration
			Card card = new Card(
					"Card", purchaseDate, paymentMethod, price, currentCustomer, null, 1,
					cardDescription, tickets, cardType
			);

			// Save the card purchase
			session.save(card);
		}
		session.flush();
	}


	private static void generateNotifications(Session session) throws Exception {
		Customer customer1 = session.get(Customer.class, 123123123);
		Customer customer2 = session.get(Customer.class, 123456789);
		Notification noti1 = new Notification("New Movie" ,"Watch \"Barbie\" today in the Cinema!\nFor more details check the movies page",
				LocalDateTime.of(2024, 9, 24, 11, 11),"Unread",customer1);
		session.save(noti1);
		Notification noti2 = new Notification("New Movie" ,"Watch \"The Joker\" today in the Cinema!\nFor more details check the movies page",
				LocalDateTime.of(2024, 9, 24, 11, 11),"Unread",customer2);
		session.save(noti2);
		session.flush();
	}

	private static void generateHomeMoviePurchases(Session session) {
		Customer customer1 = session.get(Customer.class, 123123123);
		Customer customer2 = session.get(Customer.class, 123456789);
		Customer customer3 = session.get(Customer.class, 111111111);
		Customer customer4 = session.get(Customer.class, 444555666);
		HomeMovie homeMovie = session.get(HomeMovie.class, 16);
		Screening screening = session.get(Screening.class, 652);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		HomeMoviePurchase homeMoviePurchase = new HomeMoviePurchase("Link", LocalDateTime.of(2024,9,23,21,10), "Credit Card", 120, customer2, "Movie link for \"Wire Room\".\nScreening: "+ screening.getScreeningTime().format(formatter),homeMovie,  LocalDateTime.of(2024, 9, 24, 15, 0), LocalDateTime.of(2024, 9, 24, 16, 36),screening
				,"https://www.youtube.com/watch?v=gUTjfOkVu7E", "Wire Room");

		homeMoviePurchase.setScreening(screening);
		screening.getHomeMoviePurchases().add(homeMoviePurchase);
		homeMovie.getHomeMoviePurchases().add(homeMoviePurchase);
		homeMoviePurchase.setHomeMovie(homeMovie);
		session.save(homeMoviePurchase);
		session.saveOrUpdate(screening);
		session.save(homeMoviePurchase);

		int numberOfPurchases = 60;  // Total purchases
		int purchasesPerDay = 2;  // Number of purchases per day
		double price = 90;
		String paymentMethod = "Credit Card";
		String purchaseDescription = "Movie link for \"Wire Room\".\nScreening: " + screening.getScreeningTime().format(formatter);

		// Loop to generate multiple purchases
		for (int i = 0; i < numberOfPurchases; i++) {
			// Calculate the day and time for each purchase
			int dayOfMonth = (i / purchasesPerDay) + 1;  // Ensure every day gets `purchasesPerDay` purchases
			int hourOffset = (i % purchasesPerDay) * 3;  // Spread out purchases within the same day by 3 hours

			LocalDateTime purchaseDate = LocalDateTime.of(2024, 9, dayOfMonth, 12 + hourOffset, 0);  // Purchases at different hours each day (12:00, 15:00, etc.)
			LocalDateTime availabilityStart = purchaseDate.plusDays(1).withHour(15).withMinute(0);  // Availability starts the day after purchase
			LocalDateTime availabilityEnd = availabilityStart.plusHours(1).plusMinutes(36);  // Availability lasts 1 hour and 36 minutes

			// Alternate between customers
			Customer currentCustomer = (i % 2 == 0) ? customer3 : customer4;

			// Create new HomeMoviePurchase for each iteration
			HomeMoviePurchase homeMoviePurchase1 = new HomeMoviePurchase(
					"Link", purchaseDate, paymentMethod, price, currentCustomer, purchaseDescription,
					homeMovie, availabilityStart, availabilityEnd, screening,"https://www.youtube.com/watch?v=gUTjfOkVu7E", "Wire Room");

			// Add purchase to screening and home movie
			homeMoviePurchase1.setScreening(screening);
			screening.getHomeMoviePurchases().add(homeMoviePurchase1);
			homeMovie.getHomeMoviePurchases().add(homeMoviePurchase1);

			// Save the home movie purchase
			session.save(homeMoviePurchase1);
		}

		// Save the screening and flush the session to persist changes
		session.saveOrUpdate(screening);
		session.flush();
	}


	private static void generateChangePriceRequest(Session session) throws Exception {
		Cinema cinema = session.get(Cinema.class, 1);
		ChangePriceRequest changePriceRequest1 = new ChangePriceRequest(cinema.getCardPrice(), 110, "Card", "Denied", LocalDateTime.of(2024, 9, 5, 22, 4));
		session.save(changePriceRequest1);
		session.flush();

		ChangePriceRequest changePriceRequest2 = new ChangePriceRequest(80, 100, "Ticket", "Confirmed", LocalDateTime.of(2024, 9, 1, 22, 30));
		session.save(changePriceRequest2);
		session.flush();

		ChangePriceRequest changePriceRequest3 = new ChangePriceRequest(cinema.getLinkTicketPrice(), 80, "Link Ticket", "Received", LocalDateTime.of(2024, 9, 20, 9, 55));
		session.save(changePriceRequest3);
		session.flush();
	}


	private static void generateHeadManager(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		Branch haifaCinema = session.get(Branch.class, 1);  // שליפת בתי הקולנוע מהמסד הנתונים לפי ה-ID שלהם
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);

		List<Branch> CinemaBranches = new ArrayList<>();  // יצירת רשימת בתי קולנוע עבור הסרט הראשון
		CinemaBranches.add(haifaCinema);
		CinemaBranches.add(telAvivCinema);
		CinemaBranches.add(eilatCinema);
		CinemaBranches.add(telAvivCinema);
		CinemaBranches.add(karmielCinema);
		CinemaBranches.add(jerusalemCinema);

		HeadManager headManager = new HeadManager(324122258, "Shada Ghanem", "shadaGh0512",
				"shada0512", "Head Manager", false, "0527990807",
				"shada.5.12.2001@gmail.com", "",CinemaBranches);
		session.save(headManager);
		session.flush();
	}

	private static void generateBranchManager(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		Branch haifaCinema = session.get(Branch.class, 1);
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);

		BranchManager branchManager1 = new BranchManager(200134667, "Dema Omar","dema123",
				"dema123", "Branch Manager", false, "0508873332",
				"dema@gmail.com", "Haifa Cinema", haifaCinema);
		session.save(branchManager1);
		session.flush();

		BranchManager branchManager2 = new BranchManager(304134667, "Hala Ishan","hala123",
				"hala123", "Branch Manager", false, "0520073332",
				"hala@gmail.com", "Tel Aviv Cinema", telAvivCinema);
		session.save(branchManager2);
		session.flush();

		BranchManager branchManager3 = new BranchManager(230134667, "Shatha Maree","shatha123",
				"shatha123", "Branch Manager", false, "0508855532",
				"shatha@gmail.com", "Eilat Cinema",eilatCinema);
		session.save(branchManager3);
		session.flush();

		BranchManager branchManager4 = new BranchManager(300664667, "Lian Natour","lian99",
				"lian99", "Branch Manager", false, "0508866332",
				"lain@gmail.com", "Karmiel Cinema",karmielCinema);
		session.save(branchManager4);
		session.flush();

		BranchManager branchManager5 = new BranchManager(200100667, "Rozaline Kozly","roza123",
				"roza123", "Branch Manager", false, "0520073332",
				"raza@gmail.com", "Jerusalem Cinema",jerusalemCinema);
		session.save(branchManager5);
		session.flush();
	}

	private static void generateContentManager(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		ContentManager contentManager1 = new ContentManager(300134667, "Anna Collins","anna123",
				"anna123", "Content Manager", false, "0508000032",
				"annaa@gmail.com", "");
		session.save(contentManager1);
		session.flush();
	}

	private static void generateCustomerServiceWorker(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		CustomerServiceWorker customerServiceWorker1 = new CustomerServiceWorker(300100007, "Emma Thompson","emma123",
				"emma123", "Customer Service Worker", false, "0500000032",
				"annaa@gmail.com", "");
		session.save(customerServiceWorker1);
		session.flush();
	}


	private static void generateMovies(Session session) throws Exception {
		byte[][] images = new byte[10][];
		for (int i = 1; i <= 10; i++) {
			images[i - 1] = loadImageFromFile(String.format("images/%d.jpg", i));
		}


		Movie num1 = new Movie(1, "A quiet place", "מקום שקט 2", "Michael Sarnoski", 2020, images[0],"Drama","Following the events at home, the Abbott family now face the terrors of the outside world. Forced to venture into the unknown, they realize the creatures that hunt by sound are not the only threats lurking beyond the sand path."

				,"Emily Blunt, John Krasinski, Cillian Murphy","1h 37m");

		session.save(num1);
		session.flush();

		Movie num2 = new Movie(2, "Barbie", "ברבי", "Greta Gerwig", 2023, images[1],"Comedy","Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans."

				,"Margot Robbie, Issa Rae, Ryan Gosling","1h 54m");

		session.save(num2);
		session.flush();

		Movie num3 = new Movie(3, "Fast X", "מהיר ועצבני 10", "Louis Leterrier", 2023,images[2],"Action","Dom Toretto and his family are targeted by the vengeful son of drug kingpin Hernan Reyes."

				,"Vin Diesel, Michelle Rodriguez, Jason Statham","2h 21m");

		session.save(num3);
		session.flush();

		Movie num4 = new Movie(4, "Inside out", "הקול בראש 2", "Kelsey Mann", 2024, images[3],"Adventure","A sequel that features Riley entering puberty and experiencing brand new, more complex emotions as a result. As Riley tries to adapt to her teenage years, her old emotions try to adapt to the possibility of being replaced."

				,"Amy Poehler, Maya Hawke, Kensington Tallman","1h 36m");

		session.save(num4);
		session.flush();

		Movie num5 = new Movie(5, "It Ends With Us", "איתנו זה נגמר", "Justin Baldoni", 2024, images[4],"Drama","When a woman's first love suddenly reenters her life, her relationship with a charming, but abusive neurosurgeon is upended and she realizes she must learn to rely on her own strength to make an impossible choice for her future."

				,"Blake Lively, Justin Baldoni, Jenny Slate","2h 10m");

		session.save(num5);
		session.flush();

		Movie num6 = new Movie(6, "Joker", "ג'וקר", "Todd Philips", 2019, images[5],"Drama","Arthur Fleck, a party clown and a failed stand-up comedian, leads an impoverished life with his ailing mother. However, when society shuns him and brands him as a freak, he decides to embrace the life of crime and chaos in Gotham City."

				,"Joaquin Phoenix, Robert De Niro, Zazie Beetz","2h 2m");

		session.save(num6);
		session.flush();

		Movie num7 = new Movie(7, "Oppenheimer", "אופנהיימר", "Christopher Nolan", 2023, images[6],"Documentary","The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb."

				,"Cillian Murphy, Emily Blunt, Robert Downey","3h 10m");

		session.save(num7);
		session.flush();

		Movie num8 = new Movie(8, "The creator", "היוצר", "Gareth Edward", 2023, images[7],"Action","Against the backdrop of a war between humans and robots with artificial intelligence, a former soldier finds the secret weapon, a robot in the form of a young child."

				,"John Washington, Madeleine Voyles, Gemma Chan","2h 13m");

		session.save(num8);
		session.flush();

		Movie num9 = new Movie(9, "Daughters", "הבנות", "Angela Patton", 2024, images[8],"Documentary","Four young girls prepare for a special Daddy Daughter Dance with their incarcerated fathers, as part of a unique fatherhood program in a Washington"

				,"Veronica Ngo, Ian Verdun, Sturgill Simpson","1h 48m");

		session.save(num9);
		session.flush();

		Movie num10 = new Movie(10, "In the Rearview", "מבט לאחור", "Maciek Hamela", 2023, images[9],"Documentary","A small van traverses war-torn roads, picking up Ukrainians as they abandon their homes at the front. Shuttling them across the battered landscape into exile, the van becomes a fragile refuge, a zone for its passengers' confidences."

				,"Maciek Hamela, Frances Conroy, Hannah Gross","1h 24m");

		session.save(num10);
		session.flush();

	}

	private static void generateSoonMovies(Session session) throws Exception {
		byte[] SoonImage1 = loadImageFromFile("SoonImages/1.jpg");
		byte[] SoonImage2 = loadImageFromFile("SoonImages/2.jpg");
		byte[] SoonImage3 = loadImageFromFile("SoonImages/3.jpg");

		SoonMovie soonMovie1 = new SoonMovie(1, "Blink Twice","צאי מזה" ,"Zoë Kravitz", 2024, SoonImage1, "Mystery" ,"When tech billionaire Slater King meets cocktail waitress Frida at his fundraising gala, he invites her to join him and his friends on a dream vacation on his private island. As strange things start to happen, Frida questions her reality.",
				"Naomi Ackie, Channing Tatum, Alia Shawkat", "1h42m");
		session.save(soonMovie1);
		session.flush();

		SoonMovie soonMovie2 = new SoonMovie(2, "African Giants","ענקים אפריקאים", "Omar S. Kamara", 2024, SoonImage2, "Drama", "Over a weekend visit in Los Angeles, two first-generation Sierra Leonean American brothers navigate the changing dynamics of brotherhood after a surprise announcement.",
				"Tanyell Waivers, Kathleen Kenny, Jerry Hernandez", "1h42m");
		session.save(soonMovie2);
		session.flush();

		SoonMovie soonMovie3 = new SoonMovie(3, "The Killer's Game","המשחק הרוצח" , "J.J. Perry", 2024, SoonImage3, "Action", "A veteran assassin is diagnosed with a life-threatening illness and authorizes a kill on himself. After ordering the kill, an army of former colleagues pounce and a new piece of information comes to light. Insanity ensues.",
				"Sofia Boutella, Lucy Cork, Dave Bautista", "1h44m");
		session.save(soonMovie3);
		session.flush();
	}

	private static void generateHomeMovies(Session session) throws Exception {
		byte[][] homeImages = new byte[6][];  // מערך לאחסון התמונות של סרטי הבית
		for (int i = 1; i <= 6; i++) {       //טוענים את התמונות ושומרים אותם במערך
			homeImages[i - 1] = loadImageFromFile(String.format("forHome_images/%d.jpg", i));
		}

		HomeMovie homeMovie1 = new HomeMovie(1, "Despicable Me 4", "גנוב על החיים", "Chris Renaud", 2024, homeImages[0], "https://www.youtube.com/watch?v=qQlr9-rF32A","Adventure","Gru, Lucy, Margo, Edith, and Agnes welcome a new member to the family, Gru Jr., who is intent on tormenting his dad. Gru faces a new nemesis in Maxime Le Mal and his girlfriend Valentina, and the family is forced to go on the run."
				,"Steve Carell, Kristen Wiig, Pierre Coffin","1h 34m");
		session.save(homeMovie1);
		session.flush();

		HomeMovie homeMovie2 = new HomeMovie(2, "Bad Boys", "בחורים רעים", "Adil El Arbi", 2024, homeImages[1], "https://www.youtube.com/watch?v=hRFY_Fesa9Q","Comedy","When their late police captain gets linked to drug cartels, wisecracking Miami cops Mike Lowrey and Marcus Burnett embark on a dangerous mission to clear his name."
				,"Will Smith, Martin Lawrence, Vanessa Hudgens","1h 55m");
		session.save(homeMovie2);
		session.flush();

		HomeMovie homeMovie3 = new HomeMovie(3, "Wire Room", "מלכוד בחדר", "Matt Eskandari", 2022, homeImages[2], "https://www.youtube.com/watch?v=gUTjfOkVu7E","Action","While on wire room duty, a federal agent listens in as the target is attacked in his home by a hit squad. Without burning the wire, he must protect the investigation and the target's life from the confines of a room fifty miles away."
				,"Kevin Dillon, Bruce Willis, Oliver Trevena","1h 36m");
		session.save(homeMovie3);
		session.flush();

		HomeMovie homeMovie4 = new HomeMovie(4, "Mission Impossible", "משימה לא אפשרית", "Christopher McQuarrie", 2023, homeImages[3], "https://www.youtube.com/watch?v=avz06PDqDbM&t=1s","Comedy","Ethan Hunt and his IMF team must track down a dangerous weapon before it falls into the wrong hands."
				,"Tom Cruise, Hayley Atwell, Ving Rhames","2h 43m");
		session.save(homeMovie4);
		session.flush();

		HomeMovie homeMovie5 = new HomeMovie(5, "Deadpool Wolverine", "דדפול & וולברין ", "Shawn Levy", 2024, homeImages[4], "https://www.youtube.com/watch?v=K6lJSJXSdfI","Adventure","Deadpool is offered a place in the Marvel Cinematic Universe by the Time Variance Authority, but instead recruits a variant of Wolverine to save his universe from extinction"
				,"Ryan Reynolds, Hugh Jackman, Emma Corrin","2h 8m");
		session.save(homeMovie5);
		session.flush();

		HomeMovie homeMovie6 = new HomeMovie(6, "This Time Next Year", "מזל שנפגשנו", "Nick Moore", 2024, homeImages[5], "https://www.youtube.com/watch?v=-KiYX2AvuKc&t=23s","Comedy","Minnie and Quinn are born on the same day, one minute apart. Their lives may begin together, but their worlds couldn't be more different. Years later they find themselves together again. Maybe it's time to take a chance on love."
				,"Sophie Cookson, Lucien Laviscount, Golda Rosheuvel","1h 56m");
		session.save(homeMovie6);
		session.flush();

	}
	private static void generateBranches(Session session) throws Exception {

		Movie movie1 = session.get(Movie.class, 1);
		Movie movie2 = session.get(Movie.class, 2);
		Movie movie3 = session.get(Movie.class, 3);
		Movie movie4 = session.get(Movie.class, 4);
		Movie movie5 = session.get(Movie.class, 5);
		Movie movie6 = session.get(Movie.class, 6);
		Movie movie7 = session.get(Movie.class, 7);
		Movie movie8 = session.get(Movie.class, 8);
		Movie movie9 = session.get(Movie.class, 9);
		Movie movie10 = session.get(Movie.class, 10);

		Branch haifaCinema = new Branch(1,"Haifa Cinema","Haifa");

		haifaCinema.addMovie(movie2, movie4, movie6, movie8, movie10);
		session.save(haifaCinema);

		Branch telAvivCinema = new Branch(2,"Tel Aviv Cinema","Tel Aviv");
		telAvivCinema.addMovie(movie1, movie3, movie5, movie7, movie9);
		session.save(telAvivCinema);

		Branch eilatCinema = new Branch(3,"Eilat Cinema","Eilat");
		eilatCinema.addMovie(movie1, movie2, movie5, movie6, movie9, movie10);
		session.save(eilatCinema);

		Branch karmielCinema = new Branch(4,"Karmiel Cinema","Karmiel");
		karmielCinema.addMovie(movie3, movie4, movie6, movie8, movie9, movie10);
		session.save(karmielCinema);

		Branch jerusalemCinema = new Branch(5,"Jerusalem Cinema","Jerusalem");
		jerusalemCinema.addMovie(movie1, movie2, movie3, movie7, movie8, movie9, movie10);
		session.save(jerusalemCinema);

		session.flush();
	}


	private static void generateHalls(Session session) throws Exception {

		Branch haifaCinema = session.get(Branch.class, 1);
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);
		Hall hall1 = new Hall(4, 5, 18, "1",jerusalemCinema);
		session.save(hall1);
		Hall hall2 = new Hall(5, 5, 25, "2",haifaCinema);
		session.save(hall2);
		Hall hall3 = new Hall(6, 6, 36, "3",haifaCinema);
		session.save(hall3);
		Hall hall4 = new Hall(5, 6, 28, "4",jerusalemCinema);
		session.save(hall4);
		Hall hall5 = new Hall(3, 6, 18, "5",haifaCinema);
		session.save(hall5);
		Hall hall6 = new Hall(6, 5, 30, "6",telAvivCinema);
		session.save(hall6);
		Hall hall7 = new Hall(5, 5, 23, "7",jerusalemCinema);
		session.save(hall7);
		Hall hall8 = new Hall(5, 4, 20, "8",karmielCinema);
		session.save(hall8);
		Hall hall9 = new Hall(4, 5, 18, "9",telAvivCinema);
		session.save(hall9);
		Hall hall10 = new Hall(5, 5, 23, "10",karmielCinema);
		session.save(hall10);
		Hall hall11 = new Hall(4, 4, 16, "11",karmielCinema);
		session.save(hall11);
		Hall hall12 = new Hall(4, 5, 18, "12",eilatCinema);
		session.save(hall12);
		Hall hall13 = new Hall(5, 5, 25, "13",eilatCinema);
		session.save(hall13);
		Hall hall14 = new Hall(6, 6, 36, "14",eilatCinema);
		session.save(hall14);
		Hall hall15 = new Hall(5, 6, 28, "15",telAvivCinema);
		session.save(hall15);
		Hall hall16 = new Hall(5, 6, 28, "16",jerusalemCinema);
		session.save(hall16);
		Hall hall17 = new Hall(6, 5, 30, "17",karmielCinema);
		session.save(hall17);
		Hall hall18 = new Hall(5, 5, 23, "18",karmielCinema);
		session.save(hall18);
		Hall hall19 = new Hall(5, 4, 20, "19",telAvivCinema);
		session.save(hall19);
		Hall hall20 = new Hall(4, 5, 18, "20",telAvivCinema);
		session.save(hall20);
		Hall hall21 = new Hall(5, 5, 23, "21",karmielCinema);
		session.save(hall21);
		Hall hall22 = new Hall(4, 5, 18, "22",jerusalemCinema);
		session.save(hall22);
		Hall hall23 = new Hall(5, 5, 25, "23",haifaCinema);
		session.save(hall23);
		Hall hall24 = new Hall(6, 6, 36, "24",eilatCinema);
		session.save(hall24);
		Hall hall25 = new Hall(5, 6, 28, "25",eilatCinema);
		session.save(hall25);
		Hall hall26 = new Hall(3, 6, 18, "26",jerusalemCinema);
		session.save(hall26);
		Hall hall27 = new Hall(3, 6, 18, "27",jerusalemCinema);
		session.save(hall27);
		Hall hall28 = new Hall(3, 6, 18, "28",haifaCinema);
		session.save(hall28);
		Hall hall29 = new Hall(3, 6, 18, "29",eilatCinema);
		session.save(hall29);


		session.flush();
	}

	private static void generateScreenings(Session session) throws Exception {
		Branch haifaCinema = session.get(Branch.class, 1);
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);

		Movie movie1 = session.get(Movie.class, 1);
		Movie movie2 = session.get(Movie.class, 2);
		Movie movie3 = session.get(Movie.class, 3);
		Movie movie4 = session.get(Movie.class, 4);
		Movie movie5 = session.get(Movie.class, 5);
		Movie movie6 = session.get(Movie.class, 6);
		Movie movie7 = session.get(Movie.class, 7);
		Movie movie8 = session.get(Movie.class, 8);
		Movie movie9 = session.get(Movie.class, 9);
		Movie movie10 = session.get(Movie.class, 10);

		List<LocalDateTime> screeningTimes = Arrays.asList(
				LocalDateTime.of(2024, 9, 26, 17, 00),
				LocalDateTime.of(2024, 9, 26, 20, 00),
				LocalDateTime.of(2024, 9, 26, 23, 00),
				LocalDateTime.of(2024, 9, 27, 16, 30),
				LocalDateTime.of(2024, 9, 27, 19, 30),
				LocalDateTime.of(2024, 9, 27, 23, 00),
				LocalDateTime.of(2024, 9, 28, 17, 00),
				LocalDateTime.of(2024, 9, 28, 20, 00),
				LocalDateTime.of(2024, 9, 28, 23, 00),
				LocalDateTime.of(2024, 9, 29, 17, 00),
				LocalDateTime.of(2024, 9, 29, 20, 00),
				LocalDateTime.of(2024, 9, 29, 23, 00),
				LocalDateTime.of(2024, 9, 30, 17, 00),
				LocalDateTime.of(2024, 9, 30, 20, 30),
				LocalDateTime.of(2024, 9, 30, 23, 30),
				LocalDateTime.of(2024, 10, 1, 17, 00),
				LocalDateTime.of(2024, 10, 1, 20, 30),
				LocalDateTime.of(2024, 10, 1, 23, 30),
				LocalDateTime.of(2024, 10, 2, 17, 00),
				LocalDateTime.of(2024, 10, 2, 20, 00),
				LocalDateTime.of(2024, 10, 2, 23, 00)
		);

		Hall hall1_ = session.get(Hall.class,1);
		Hall hall2_ = session.get(Hall.class,2);
		Hall hall3_ = session.get(Hall.class,3);
		Hall hall4_ = session.get(Hall.class,4);
		Hall hall5_ = session.get(Hall.class,5);
		Hall hall6_ = session.get(Hall.class,6);
		Hall hall7_ =session.get(Hall.class,7);
		Hall hall8_ =session.get(Hall.class,8);
		Hall hall9_ = session.get(Hall.class,9);
		Hall hall10_ = session.get(Hall.class,10);
		Hall hall11_ = session.get(Hall.class,11);
		Hall hall12_ =session.get(Hall.class,12);
		Hall hall13_ =session.get(Hall.class,13);
		Hall hall14_ = session.get(Hall.class,14);
		Hall hall15_ = session.get(Hall.class,15);
		Hall hall16_ = session.get(Hall.class,16);
		Hall hall17_ = session.get(Hall.class,17);
		Hall hall18_ = session.get(Hall.class,18);
		Hall hall19_ = session.get(Hall.class,19);
		Hall hall20_ =session.get(Hall.class,20);
		Hall hall21_ = session.get(Hall.class,21);
		Hall hall22_ =session.get(Hall.class,22);
		Hall hall23_ =session.get(Hall.class,23);
		Hall hall24_ =session.get(Hall.class,24);
		Hall hall25_ = session.get(Hall.class,25);
		Hall hall26_ = session.get(Hall.class,26);
		Hall hall27_ =session.get(Hall.class,27);
		Hall hall28_ = session.get(Hall.class,28);
		Hall hall29_ = session.get(Hall.class,29);


		for (LocalDateTime time : screeningTimes) {
			movie1.addScreening(time, jerusalemCinema, hall1_);
			movie1.addScreening(time, telAvivCinema,hall6_);
			movie1.addScreening(time, eilatCinema,hall12_);

			movie2.addScreening(time, haifaCinema, hall2_);
			movie2.addScreening(time, jerusalemCinema, hall7_);
			movie2.addScreening(time, eilatCinema, hall13_);

			movie3.addScreening(time, karmielCinema,hall17_);
			movie3.addScreening(time, jerusalemCinema,hall22_);
			movie3.addScreening(time, telAvivCinema,hall15_);


			movie4.addScreening(time, karmielCinema,hall18_);
			movie4.addScreening(time, haifaCinema,hall23_);

			movie5.addScreening(time, telAvivCinema,hall19_);
			movie5.addScreening(time, eilatCinema,hall24_);

			movie6.addScreening(time, haifaCinema, hall3_);
			movie6.addScreening(time, karmielCinema, hall8_);
			movie6.addScreening(time, eilatCinema, hall14_);

			movie7.addScreening(time, jerusalemCinema, hall4_);
			movie7.addScreening(time, telAvivCinema, hall9_);

			movie8.addScreening(time, haifaCinema, hall5_);
			movie8.addScreening(time, karmielCinema, hall10_);
			movie8.addScreening(time, jerusalemCinema, hall16_);

			movie9.addScreening(time, telAvivCinema,hall20_);
			movie9.addScreening(time, eilatCinema,hall25_);
			movie9.addScreening(time, karmielCinema, hall11_);
			movie9.addScreening(time, jerusalemCinema,hall27_);

			movie10.addScreening(time, karmielCinema,hall21_);
			movie10.addScreening(time, jerusalemCinema,hall26_);
			movie10.addScreening(time, haifaCinema,hall28_);
			movie10.addScreening(time, eilatCinema,hall29_);

		}

		List<Movie> movies = Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9, movie10);
		for (Movie movie : movies) {
			session.save(movie);
		}
		session.flush();

		Movie movie14 = session.get(Movie.class, 14);
		Movie movie15 = session.get(Movie.class, 15);
		Movie movie16 = session.get(Movie.class, 16);
		Movie movie17 = session.get(Movie.class, 17);
		Movie movie18 = session.get(Movie.class, 18);
		Movie movie19 = session.get(Movie.class, 19);


		List<LocalDateTime> HomeScreeningTimes = Arrays.asList(
				LocalDateTime.of(2024, 9, 26, 15, 00),
				LocalDateTime.of(2024, 9, 26, 19, 00),
				LocalDateTime.of(2024, 9, 26, 23, 00),
				LocalDateTime.of(2024, 9, 27, 15, 00),
				LocalDateTime.of(2024, 9, 27, 19, 00),
				LocalDateTime.of(2024, 9, 27, 23, 00),
				LocalDateTime.of(2024, 9, 28, 15, 00),
				LocalDateTime.of(2024, 9, 28, 19, 00),
				LocalDateTime.of(2024, 9, 28, 23, 00),
				LocalDateTime.of(2024, 9, 29, 15, 00),
				LocalDateTime.of(2024, 9, 29, 19, 00),
				LocalDateTime.of(2024, 9, 29, 23, 00),
				LocalDateTime.of(2024, 9, 30, 15, 00),
				LocalDateTime.of(2024, 9, 30, 19, 00),
				LocalDateTime.of(2024, 9, 30, 23, 00),
				LocalDateTime.of(2024, 10, 1, 15, 00),
				LocalDateTime.of(2024, 10, 1, 19, 00),
				LocalDateTime.of(2024, 10, 1, 23, 00),
				LocalDateTime.of(2024, 10, 2, 15, 00),
				LocalDateTime.of(2024, 10, 2, 19, 00),
				LocalDateTime.of(2024, 10, 2, 23, 00)
		);
		for (LocalDateTime time : HomeScreeningTimes) {
			movie14.addScreening(time, null,null);
			movie15.addScreening(time, null,null);
			movie16.addScreening(time, null,null);
			movie17.addScreening(time, null,null);
			movie18.addScreening(time, null,null);
			movie19.addScreening(time, null,null);
		}

		session.save(movie14);
		session.save(movie15);
		session.save(movie16);
		session.save(movie17);
		session.save(movie18);
		session.save(movie19);

		session.flush();
	}

	private static List<Purchase> getAllPurchases(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Purchase> query = builder.createQuery(Purchase.class);
		query.from(Purchase.class);
		return session.createQuery(query).getResultList();
	}

	// Fetch purchases based on productType for both links and cards
	List<Purchase> getMonthlyPurchases(Session session, int year, int month) {
		try {
			// Retrieve purchases with productType 'Link' for home movie links
			List<Purchase> linkPurchases = session.createQuery("FROM Purchase WHERE productType = 'Link' AND YEAR(purchaseDate) = :year AND MONTH(purchaseDate) = :month", Purchase.class)
					.setParameter("year", year)
					.setParameter("month", month)
					.getResultList();

			// Retrieve purchases with productType 'Card' for regular cards
			List<Purchase> cardPurchases = session.createQuery("FROM Purchase WHERE productType = 'Card' AND YEAR(purchaseDate) = :year AND MONTH(purchaseDate) = :month", Purchase.class)
					.setParameter("year", year)
					.setParameter("month", month)
					.getResultList();

			// Combine the two lists
			List<Purchase> allPurchases = new ArrayList<>(linkPurchases);
			allPurchases.addAll(cardPurchases);

			// Debugging: print the results
			/*for (Purchase purchase : allPurchases) {
				System.out.println("Purchase: " + purchase.getPurchaseDescription() + ", Product Type: " + purchase.getProductType() + ", Date: " + purchase.getPurchaseDate());
			}*/

			return allPurchases;  // Return combined purchases
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	private Map<LocalDate, DailyReportData> generateMonthlyReportData(List<Purchase> purchases) {
		Map<LocalDate, DailyReportData> reportData = new HashMap<>();

		// Process all purchases
		for (Purchase purchase : purchases) {
			LocalDate purchaseDate = purchase.getPurchaseDate().toLocalDate();
			reportData.putIfAbsent(purchaseDate, new DailyReportData(purchaseDate));
			DailyReportData dailyData = reportData.get(purchaseDate);

			// Handle based on productType
			if (purchase.getProductType().equals("Link")) {
				dailyData.addLinkSold();
				dailyData.addLinkRevenue(purchase.getPricePaid());
			} else if (purchase.getProductType().equals("Card")) {
				dailyData.addCardSold();
				dailyData.addCardRevenue(purchase.getPricePaid());
			}
		}

		return reportData;
	}

	private static List<Card> getAllCards(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Card> query = builder.createQuery(Card.class);
		query.from(Card.class);
		return session.createQuery(query).getResultList();
	}

	private static List<Screening> getAllScreenings(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Screening> query = builder.createQuery(Screening.class);
		query.from(Screening.class);
		return session.createQuery(query).getResultList();
	}

	private static List<Notification> getAllNotifications(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Notification> query = builder.createQuery(Notification.class);
		query.from(Notification.class);
		return session.createQuery(query).getResultList();
	}

	// for testing
	private static List<Customer> getAllCustomers(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		query.from(Customer.class);
		return session.createQuery(query).getResultList();
	}


	private static List<Movie> getAllMovies(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
		query.from(Movie.class);
		return session.createQuery(query).getResultList();
	}

	private static List<ChangePriceRequest> getAllRequests(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<ChangePriceRequest> query = builder.createQuery(ChangePriceRequest.class);
		query.from(ChangePriceRequest.class);
		return session.createQuery(query).getResultList();
	}

	private static byte[] loadImageFromFile(String relativePath) throws IOException {
		InputStream inputStream = SimpleServer.class.getClassLoader().getResourceAsStream(relativePath);
		if (inputStream == null) {
			throw new FileNotFoundException("File not found: " + relativePath);
		}
		return inputStream.readAllBytes();
	}


	private static List<SoonMovie> getAllSoonMovies(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<SoonMovie> query = builder.createQuery(SoonMovie.class);
		query.from(SoonMovie.class);
		return session.createQuery(query).getResultList();
	}

	private static List<HomeMovie> getAllHomeMovies(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<HomeMovie> query = builder.createQuery(HomeMovie.class);
		query.from(HomeMovie.class);
		return session.createQuery(query).getResultList();
	}

	private static List<Branch> getAllBranches(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Branch> query = builder.createQuery(Branch.class);
		query.from(Branch.class);
		return session.createQuery(query).getResultList();
	}

	private static List<HomeMoviePurchase> getAllHomeMoviePurchases(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<HomeMoviePurchase> query = builder.createQuery(HomeMoviePurchase.class);
		query.from(HomeMoviePurchase.class);
		return session.createQuery(query).getResultList();
	}

	private static HeadManager getHeadManager(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<HeadManager> query = builder.createQuery(HeadManager.class);
		query.from(HeadManager.class);
		return session.createQuery(query).getSingleResult();
	}

	private static Cinema cinema(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Cinema> query = builder.createQuery(Cinema.class);
		query.from(Cinema.class);
		return session.createQuery(query).getSingleResult();
	}

	private static List<BranchManager> getBranchManager(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<BranchManager> query = builder.createQuery(BranchManager.class);
		query.from(BranchManager.class);
		return session.createQuery(query).getResultList();
	}

	private static ContentManager getContentManager(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<ContentManager> query = builder.createQuery(ContentManager.class);
		query.from(ContentManager.class);
		return session.createQuery(query).getSingleResult();
	}

	private static CustomerServiceWorker getCustomerServiceWorker(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CustomerServiceWorker> query = builder.createQuery(CustomerServiceWorker.class);
		query.from(CustomerServiceWorker.class);
		return session.createQuery(query).getSingleResult();
	}

	private static List<Employee> getAllEmployees(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		query.from(Employee.class);
		return session.createQuery(query).getResultList();
	}

	private static List<Screening> getScreeningsForMovie(Session session, int movieId) {
		Movie movie = session.get(Movie.class, movieId);
		return new ArrayList<>(movie.getScreenings());
	}

	private static List<Complaint> getAllComplaints(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Complaint> query = builder.createQuery(Complaint.class);
		query.from(Complaint.class);
		return session.createQuery(query).getResultList();
	}

	private Map<Integer, Integer> getComplaintCountByDayAndBranch(Session session, int month, String branchName) throws Exception {
		// Initialize the CriteriaBuilder and create a query
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = builder.createTupleQuery();  // Tuple to handle multiple fields in result

		// Define the root of the query (the Complaint entity)
		Root<Complaint> root = query.from(Complaint.class);

		// Assuming 'submissionDate' is the field representing the date of the complaint
		// Extract the day, month, and year from the date
		Expression<Integer> dayExpression = builder.function("DAY", Integer.class, root.get("submissionDate"));
		Expression<Integer> monthExpression = builder.function("MONTH", Integer.class, root.get("submissionDate"));
		Expression<Integer> yearExpression = builder.function("YEAR", Integer.class, root.get("submissionDate"));

		// Select the day and the count of complaints
		query.multiselect(dayExpression, builder.count(root.get("id")));  // Replace "id" with your complaint identifier field

		// Add conditions to filter by the specified month and year
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(monthExpression, month));
		predicates.add(builder.equal(yearExpression, 2024));

		if (!branchName.equals("All")) {
			predicates.add(builder.equal(root.get("subject"), branchName)); // Filter by branch name if provided
		}

		// Apply the predicates to the query
		query.where(predicates.toArray(new Predicate[0]));

		// Group by the day of the month
		query.groupBy(dayExpression);

		// Execute the query and store the result as a list of tuples
		List<Tuple> results = session.createQuery(query).getResultList();

		// Convert the result to a Map<Integer, Integer> where the key is the day and the value is the count
		Map<Integer, Integer> complaintCountByDay = new HashMap<>();
		for (Tuple tuple : results) {
			Integer day = tuple.get(0, Integer.class);
			Long count = tuple.get(1, Long.class); // Note: Count is returned as Long
			complaintCountByDay.put(day, count.intValue()); // Convert to Integer if necessary
		}

		return complaintCountByDay;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		NewMessage message = (NewMessage) msg;
		String msgString = message.getMessage();
		try {
			if (msgString.isBlank()) {
				message.setMessage("Error! we got an empty message");
				client.sendToClient(message);
			} else if (msgString.equals("moviesList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage = new NewMessage(movies, "movies");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("soonMoviesList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים קרובים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<SoonMovie> soonMovies = getAllSoonMovies(session);
					NewMessage newMessage = new NewMessage(soonMovies, "soonMovies");  // שליחת רשימת הסרטים הקרובים ללקוח עם המחרוזת "soonMovies"
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("homeMoviesList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים לצפייה מהבית
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<HomeMovie> homeMovies = getAllHomeMovies(session);
					NewMessage newMessage = new NewMessage(homeMovies, "homeMovies");  // שליחת רשימת הסרטים לבית ללקוח עם המחרוזת "homeMovies"
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("login")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					String userNameString = message.getUserName();
					String passwordString = message.getPassword();
					List<Employee> employees = getAllEmployees(session);
					NewMessage newMessage;
					int flag = 0;
					for (Employee employee : employees) {
						flag = 0;
						if (userNameString.equals(employee.getUsername()) && passwordString.equals(employee.getPassword())) {
							if (employee.isOnline()) {
								newMessage = new NewMessage("Alreadylogin");
							} else {
								employee.setIsOnline(true);
								newMessage = new NewMessage(employee, "login");
							}
							client.sendToClient(newMessage);
							session.getTransaction().commit();
							break;
						} else flag = 1;
					}
					if (flag == 1) {
						newMessage = new NewMessage("loginDenied");
						client.sendToClient(newMessage);
						session.getTransaction().commit();
					}

				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("logOut")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Employee> employees = getAllEmployees(session);
					Employee employee = message.getEmployee();
					if(employee != null) {
						for (Employee emp : employees) {
							if (emp.getId() == employee.getId()) {
								emp.setIsOnline(false);
							}
						}
						NewMessage newMessage = new NewMessage("logOut");
						client.sendToClient(newMessage);
					}
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("screeningTimesRequest")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Movie requestedMovie = message.getMovie();
					List<Screening> screenings = session.createQuery(
									"from Screening where movie.id = :movieId", Screening.class)
							.setParameter("movieId", requestedMovie.getId())
							.getResultList();
					NewMessage newMessage = new NewMessage(screenings, "screeningTimes");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			}else if (msgString.equals("allScreeningTimesRequest")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Screening> screenings = getAllScreenings(session);
					NewMessage newMessage = new NewMessage(screenings, "screeningTimes");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			}  else if (msgString.equals("removeHomeMovie")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					HomeMovie movie = (HomeMovie) message.getObject();
					HomeMovie movieToDelete = session.get(HomeMovie.class, movie.getId());
					List<HomeMoviePurchase> homeMoviePurchases = movieToDelete.getHomeMoviePurchases();
					List<HomeMoviePurchase> homeMoviePurchases2 = new ArrayList<>(movieToDelete.getHomeMoviePurchases());
					for (HomeMoviePurchase purchase : homeMoviePurchases2) {
						HomeMoviePurchase purchase1 = session.get(HomeMoviePurchase.class, purchase.getId());
						movieToDelete.removeHomeMoviePurchase(purchase1);
						purchase1.setHomeMovie(null); // Break the link, adjust as per your entity design
						purchase1.setScreening(null);
						session.update(purchase1);
						session.flush();
					}
					movieToDelete.getHomeMoviePurchases().clear();
					session.saveOrUpdate(movieToDelete);
					session.remove(movieToDelete);
					List<Movie> movies = getAllMovies(session);
					List<HomeMovie> homeMovies = getAllHomeMovies(session);
					NewMessage newMessage = new NewMessage(homeMovies, "homeMovies");
					sendToAllClients(newMessage);
					NewMessage newMessage3 = new NewMessage(movies, "movies");
					client.sendToClient(newMessage3);
					NewMessage newMessage2 = new NewMessage("movieRemoved");
					client.sendToClient(newMessage2);
					NewMessage newMessage4 = new NewMessage(movie.getId(), "movieNotAvailable");
					sendToAllClients(newMessage4);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("removeCinemaMovie")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Movie movie = (Movie) message.getObject();
					Movie movieToDelete = session.get(Movie.class, movie.getId());
					String branch = message.getBranchName();
					if (branch.equals("All") || movieToDelete.getBranches().size() == 1) {
						session.remove(movieToDelete);
						NewMessage newMessage4 = new NewMessage(movie.getId(), "movieNotAvailable", "All");
						sendToAllClients(newMessage4);
						NewMessage newMessage2 = new NewMessage("movieRemoved");
						client.sendToClient(newMessage2);
					} else {
						Movie movieToDeleteBranch = session.get(Movie.class, movie.getId());
						List<Branch> branches = getAllBranches(session);
						List<Screening> screenings = getScreeningsForMovie(session, movie.getId());
						for (Screening screening : screenings) {
							if (screening.getBranch().getName().equals(branch)) {
								movieToDeleteBranch.getScreenings().remove(screening);
								session.remove(screening);
							}
						}
						for (Branch x : branches) {
							if (x.getName().equals(branch)) {
								x.getMovies().remove(movie);
								movieToDeleteBranch.getBranches().remove(x);
							}
						}
						if (movieToDeleteBranch != null) {
							List<Screening> screeningsForMovie = getScreeningsForMovie(session, movie.getId());
							NewMessage newMessage3 = new NewMessage(screeningsForMovie, "screeningTimes");
							sendToAllClients(newMessage3);
						}
						NewMessage newMessage2 = new NewMessage("movieRemoved");
						client.sendToClient(newMessage2);
						NewMessage newMessage4 = new NewMessage(movie.getId(), "movieNotAvailable", branch);
						sendToAllClients(newMessage4);
					}
					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage = new NewMessage(movies, "movies");
					sendToAllClients(newMessage);

					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("addCinemaMovie")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים לצפייה מהבית
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Movie movie = (Movie) message.getObject();
					List<Branch> branches = getAllBranches(session);
					List<Branch> movieBranches = message.getBranches();
					List<Branch> newBranches = new ArrayList<>();
					List<Screening> screenings = new ArrayList<>();
					List<LocalDateTime> times = message.getDateTimes();
					List<Hall> halls = message.getHalls();
					int i;
					for (Branch x : branches) {
						i = 0;
						for (Branch y : movieBranches) {
							if (x.getName().equals(y.getName())) {
								if (!newBranches.contains(x))
									newBranches.add(x);
								if (!x.getMovies().contains(movie))
									x.addMovie(movie);
								session.save(x);
								Screening screening = new Screening(times.get(i), movie, x);
								screenings.add(screening);
								Hall hall = session.get(Hall.class, halls.get(i).getId());
								movie.addScreening(times.get(i), x, hall);  /////////////////////////////////////////////////////////
							}
							i++;
						}
					}
					/*for(Branch y : movieBranches) {
						String hql = "from Branch where name = :branchName";
						Query<Branch> query = session.createQuery(hql, Branch.class);
						query.setParameter("branchName", y.getName());
						Branch branch = query.getSingleResult();
						branch.getMovies().add(movie);
						session.save(branch);
						newBranches.add(branch);
						Screening screening = new Screening(times.get(i), movie, branch);
						screenings.add(screening);
						i++;
					}*/
					movie.setBranches(newBranches);
					//movie.setScreenings(screenings);
					session.save(movie);
					session.flush();
					for(Screening screening : movie.getScreenings()) {
						session.save(screening);
						session.flush();
					}
					NewMessage newMessage = new NewMessage("movieAdded");  // שליחת רשימת הסרטים לבית ללקוח עם המחרוזת "homeMovies"
					client.sendToClient(newMessage);
					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage1 = new NewMessage(movies, "movies");
					sendToAllClients(newMessage1);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("addHomeMovie")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים לצפייה מהבית
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					HomeMovie homeMovie = (HomeMovie) message.getObject();
					session.save(homeMovie);
					NewMessage newMessage = new NewMessage("movieAdded");  // שליחת רשימת הסרטים לבית ללקוח עם המחרוזת "homeMovies"
					client.sendToClient(newMessage);
					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage1 = new NewMessage(movies, "movies");
					client.sendToClient(newMessage1);
					//sendToAllClients(newMessage1);
					List<HomeMovie> homeMovies = getAllHomeMovies(session);
					NewMessage newMessage2 = new NewMessage(homeMovies, "homeMovies");
					sendToAllClients(newMessage2);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("removeScreening")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();

					// קבלת הנתונים מההודעה
					ScreeningData data = (ScreeningData) message.getObject();
					Movie movie = session.get(Movie.class, data.getMovieId());

					if (movie instanceof HomeMovie) {
						HomeMovie homeMovie = session.get(HomeMovie.class, movie.getId());
						LocalDateTime screeningTime = data.getScreeningTime();

						// חיפוש ההקרנה להסרה
						Screening screeningToRemove = movie.getScreenings().stream()
								.filter(screening -> screening.getScreeningTime().equals(screeningTime))
								.findFirst()
								.orElse(null);

						if (screeningToRemove != null) {
							movie.getScreenings().remove(screeningToRemove); // הסרת ההקרנה מהסרט
							session.remove(screeningToRemove); // הסרה מהמסד
						} else {
							System.err.println("Screening not found.");
						}
						session.save(movie); ///////////////////////
					} else {
						Branch branch = session.get(Branch.class, data.getBranchId());
						LocalDateTime screeningTime = data.getScreeningTime();

						if (movie == null || branch == null) {
							System.err.println("Movie or branch not found.");
							return;
						}

						// חיפוש ההקרנה להסרה
						Screening screeningToRemove = movie.getScreenings().stream()
								.filter(screening -> screening.getScreeningTime().equals(screeningTime) && screening.getBranch().equals(branch))
								.findFirst()
								.orElse(null);

						if (screeningToRemove != null) {
							movie.getScreenings().remove(screeningToRemove); // הסרת ההקרנה מהסרט
							branch.getScreenings().remove(screeningToRemove); // הסרת ההקרנה מהסניף
							session.remove(screeningToRemove); // הסרה מהמסד
						} else {
							System.err.println("Screening not found.");
						}
						session.save(movie); ///////////////////////
					}


					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage = new NewMessage(movies, "movies");
					sendToAllClients(newMessage);

					List<HomeMovie> homeMovies = getAllHomeMovies(session);
					NewMessage homeMoviesMessage = new NewMessage(homeMovies, "homeMovies");
					sendToAllClients(homeMoviesMessage);

					NewMessage newMessage2 = new NewMessage("screeningRemoved");
					client.sendToClient(newMessage2);

					List<Screening> screenings = getScreeningsForMovie(session, movie.getId());
					NewMessage newMessage3 = new NewMessage(screenings, "screeningTimes");
					sendToAllClients(newMessage3);

					session.getTransaction().commit();

				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("addScreening")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();

					// Extract screening data from the message
					ScreeningData data = (ScreeningData) message.getObject();
					Movie movie = session.get(Movie.class, data.getMovieId());
					Branch branch = data.getBranchId() != null ? session.get(Branch.class, data.getBranchId()) : null;
					Hall hall = data.getHallId() != null ? session.get(Hall.class, data.getHallId()) : null;
					LocalDateTime screeningTime = data.getScreeningTime();

					// Check if the screening already exists
					boolean screeningExists = movie.getScreenings().stream()
							.anyMatch(screening -> screening.getScreeningTime().equals(screeningTime)
									&& screening.getBranch().equals(branch)
									&& screening.getHall().equals(hall));

					if (!screeningExists) {
						// Create and add the new screening
						Screening newScreening = new Screening();
						newScreening.setMovie(movie);
						newScreening.setScreeningTime(screeningTime);
						newScreening.setBranch(branch);
						newScreening.setHall(hall);

						session.persist(newScreening);
						movie.getScreenings().add(newScreening);

						if (branch != null) {
							branch.getScreenings().add(newScreening);
						}
						if (hall != null) {
							hall.getScreenings().add(newScreening);
						}

						session.getTransaction().commit();

						// Load updated movie list and send to all clients
						List<Movie> movies = getAllMovies(session);
						NewMessage updateMessage = new NewMessage(movies, "movies");
						sendToAllClients(updateMessage);

						List<HomeMovie> homeMovies = getAllHomeMovies(session);
						NewMessage homeMoviesMessage = new NewMessage(homeMovies, "homeMovies");
						sendToAllClients(homeMoviesMessage);

						// Send success message to the client who initiated the request
						NewMessage newMessage = new NewMessage("screeningAdded");
						client.sendToClient(newMessage);

						List<Screening> screenings = getScreeningsForMovie(session, movie.getId());
						NewMessage newMessage3 = new NewMessage(screenings, "screeningTimes");
						sendToAllClients(newMessage3);
					} else {
						session.getTransaction().rollback();
						NewMessage newMessage = new NewMessage("Screening already exists");
						client.sendToClient(newMessage);
					}
				} catch (Exception exception) {
					System.err.println("An error occurred while adding a screening: " + exception.getMessage());
				}
			} else if (msgString.equals("editScreening")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();

					// Extract screening data from the message
					ScreeningData data = (ScreeningData) message.getObject();
					Movie movie = session.get(Movie.class, data.getMovieId());
					Branch branch = data.getBranchId() != null ? session.get(Branch.class, data.getBranchId()) : null;
					LocalDateTime newScreeningTime = data.getScreeningTime();

					// Attempt to find the existing screening to update
					Screening screeningToUpdate = movie.getScreenings().stream()
							.filter(screening -> screening.getBranch().equals(branch))
							.filter(screening -> !screening.getScreeningTime().equals(newScreeningTime)) // Ensure we're not comparing with the new time
							.findFirst().orElse(null);

					if (screeningToUpdate != null) {
						// Update the screening time
						screeningToUpdate.setScreeningTime(newScreeningTime);
						session.update(screeningToUpdate);

						// Commit transaction
						session.getTransaction().commit();

						// Load updated movie list and send to all clients
						List<Movie> movies = getAllMovies(session);
						NewMessage updateMessage = new NewMessage(movies, "movies");
						sendToAllClients(updateMessage);

						List<HomeMovie> homeMovies = getAllHomeMovies(session);
						NewMessage homeMoviesMessage = new NewMessage(homeMovies, "homeMovies");
						sendToAllClients(homeMoviesMessage);

						// Send success message to the client who initiated the request
						NewMessage successMessage = new NewMessage("Screening updated");
						client.sendToClient(successMessage);

						List<Screening> screenings = getScreeningsForMovie(session, movie.getId());
						NewMessage screeningTimesMessage = new NewMessage(screenings, "screeningTimes");
						sendToAllClients(screeningTimesMessage);
					} else {
						session.getTransaction().rollback();
						NewMessage errorMessage = new NewMessage("Screening not found");
						client.sendToClient(errorMessage);
					}
				} catch (Exception exception) {
					System.err.println("An error occurred while editing a screening: " + exception.getMessage());
				}
			} else if (msgString.equals("submitComplaint")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Complaint complaint = (Complaint) message.getObject();
					session.save(complaint);
					NewMessage newMessage = new NewMessage("complaintSubmitted");
					client.sendToClient(newMessage);
					List<Complaint> complaints = getAllComplaints(session);
					NewMessage newMessage2 = new NewMessage(complaints, "complaints");
					sendToAllClients(newMessage2);
					long id = complaint.getCustomerID();
					int id2 = (int) id;
					Customer customer = session.get(Customer.class, id2);
					if (customer == null) {

						Customer customer1 = new Customer(id2, complaint.getName(), complaint.getEmail(),
								complaint.getPhoneNumber(), null, false);
						session.save(customer1);
					}
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("complaintsList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Complaint> complaints = getAllComplaints(session);
					NewMessage newMessage = new NewMessage(complaints, "complaints");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("answerComplaint")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Complaint complaint = (Complaint) message.getObject();
					Complaint complaintToAnswer = session.get(Complaint.class, complaint.getId());
					complaintToAnswer.setResponse(complaint.getResponse());
					complaintToAnswer.setStatus(true);
					session.save(complaintToAnswer);
					List<Complaint> complaints = getAllComplaints(session);
					NewMessage newMessage = new NewMessage(complaints, "complaints");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					sendToAllClients(newMessage);
					NewMessage newMessage2 = new NewMessage("complaintAnswered");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					client.sendToClient(newMessage2);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("changePriceRequest")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					ChangePriceRequest changePriceRequest = (ChangePriceRequest) message.getObject();
					session.save(changePriceRequest);
					List<ChangePriceRequest> requests = getAllRequests(session);
					NewMessage newMessage = new NewMessage(requests, "requests");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					sendToAllClients(newMessage);
					NewMessage newMessage2 = new NewMessage("requestReceived");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					client.sendToClient(newMessage2);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("requestsList")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<ChangePriceRequest> requests = getAllRequests(session);
					NewMessage newMessage = new NewMessage(requests, "requests");
					sendToAllClients(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("confirmPriceUpdate")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Cinema cinema = session.get(Cinema.class, 1);
					ChangePriceRequest changePriceRequest = (ChangePriceRequest) message.getObject();
					List<ChangePriceRequest> requests = getAllRequests(session);
					ChangePriceRequest request = session.get(ChangePriceRequest.class, changePriceRequest.getId());
					request.setStatus("Confirmed");
					switch (changePriceRequest.getProduct()) {
						case "Ticket":
							cinema.setTicketPrice(changePriceRequest.getNewPrice());
							break;
						case "Link Ticket":
							cinema.setLinkTicketPrice(changePriceRequest.getNewPrice());
							break;
						case "Card":
							cinema.setCardPrice(changePriceRequest.getNewPrice());
					}
					session.save(cinema);
					session.save(request);
					NewMessage newMessage = new NewMessage(requests, "requests");
					sendToAllClients(newMessage);
					NewMessage newMessage2 = new NewMessage(cinema, "cinema");
					sendToAllClients(newMessage2);
					NewMessage newMessage3 = new NewMessage("requestConfirmed");
					client.sendToClient(newMessage3);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("denyPriceUpdate")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					ChangePriceRequest changePriceRequest = (ChangePriceRequest) message.getObject();
					List<ChangePriceRequest> requests = getAllRequests(session);
					ChangePriceRequest request = session.get(ChangePriceRequest.class, changePriceRequest.getId());
					request.setStatus("Denied");
					session.save(request);
					NewMessage newMessage = new NewMessage(requests, "requests");
					sendToAllClients(newMessage);
					NewMessage newMessage2 = new NewMessage("requestDenied");
					client.sendToClient(newMessage2);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("cinema")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Cinema cinema = session.get(Cinema.class, 1);
					NewMessage newMessage = new NewMessage(cinema, "cinema");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
				}
			} else if (msgString.equals("loginCustomer")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					int idNumber = message.getId();
					List<Customer> customers = getAllCustomers(session);
					NewMessage newMessage;
					boolean found = false;

					for (Customer customer : customers) {
						if (idNumber == customer.getId()) {
							found = true;
							if (customer.isLoggedIn()) {
								newMessage = new NewMessage("AlreadyloginCustomer");
							} else {
								customer.setLoggedIn(true);
								newMessage = new NewMessage(customer, "loginCustomer");
							}
							client.sendToClient(newMessage);
							session.getTransaction().commit();
							break;
						}
					}

					if (!found) {
						newMessage = new NewMessage("loginDeniedCustomer");
						client.sendToClient(newMessage);
						session.getTransaction().commit();
					}

				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("logOutCustomer")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Customer customer = (Customer) message.getObject();
					Customer customerToLogOut = session.get(Customer.class, customer.getId());
					if(customerToLogOut != null) {
						customerToLogOut.setLoggedIn(false);  // log out
						//session.saveOrUpdate(customer);
						session.saveOrUpdate(customerToLogOut);
					}
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred while logging out.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("cards")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Card> cards = getAllCards(session);
					NewMessage newMessage = new NewMessage(cards, "cardsList");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
				}
			} else if (msgString.equals("purchaseCards")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Purchase> purchaseCards = (List<Purchase>) message.getObject();
					for (Purchase card : purchaseCards) {
						session.save(card);
					}
					Customer customer = purchaseCards.get(0).getCustomer();
					Customer savedCustomer = session.get(Customer.class, customer.getId());
					if (savedCustomer == null) {
						session.save(customer);
						//List<Purchase> purchases = customer.getPurchaseHistory();
						List<Purchase> purchases = getAllPurchases(session);
						NewMessage responseMessage = new NewMessage(purchases, "purchasesResponse");
						sendToAllClients(responseMessage);  ////////////////////////////////////////////////////////
					} else {
						for (Purchase purchase : purchaseCards) {
							savedCustomer.addPurchase(purchase);
							session.save(savedCustomer);
							//List<Purchase> purchases = customer.getPurchaseHistory();
							List<Purchase> purchases = getAllPurchases(session);
							NewMessage responseMessage = new NewMessage(purchases, "purchasesResponse");
							sendToAllClients(responseMessage);  ////////////////////////////////////////////////////////
						}
					}
					NewMessage newMessage1 = new NewMessage("purchaseSuccessful");
					client.sendToClient(newMessage1);
					List<Card> cards = getAllCards(session);
					NewMessage newMessage = new NewMessage(cards, "cardsList");
					sendToAllClients(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
				}
			} else if (msgString.equals("notifications")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Notification> notifications = getAllNotifications(session);
						List<Customer> customers = getAllCustomers(session);
						List<Movie> movies = getAllMovies(session);
						//movies.removeIf(movie -> movie instanceof HomeMovie);
						movies.removeIf(movie -> movie instanceof SoonMovie);
					for (Movie movie : movies) {
						boolean hasScreeningToday = false;
						List<Screening> screenings = movie.getScreenings();
						// Check if this movie has a screening today
						for (Screening screening : screenings) {
							if (screening.getScreeningTime().toLocalDate().equals(LocalDate.now())) {
								hasScreeningToday = true;
								break; // Exit this loop as we only need to know if one matches
							}}
						// If the movie has a screening today, notify each customer who has a card purchase
						if (hasScreeningToday) {
							for (Customer customer : customers) {
								List<Purchase> purchases = customer.getPurchaseHistory();
								for (Purchase purchase : purchases) {
									if (purchase instanceof Card && !(movie instanceof HomeMovie)) {
										Notification cardNotification = new Notification(
												"New Movie", "Watch \"" + movie.getEngtitle() + "\" today in the Cinema!\nFor more details check the movies page.",
												LocalDateTime.now(), "Unread", customer);
										int flag = 0;
										for(Notification notification : notifications) {
											if(notification.getCustomer().equals(customer) && notification.getMessage().equals(cardNotification.getMessage()) && notification.getTime().toLocalDate().equals(cardNotification.getTime().toLocalDate())) {
												flag = 1;
												break;}}
										if(flag == 0)
											session.save(cardNotification);
										break; // Exit the purchase loop for this customer because a notification is sent
									}

								}}}}
						for (Customer customer : customers) {
							List<Purchase> purchases = customer.getPurchaseHistory();
							for (Purchase purchase : purchases) {
								if(purchase instanceof HomeMoviePurchase) { // && movie instanceof HomeMovie) {
									/*	String movieTitle = "";
										if(((HomeMoviePurchase) purchase).getHomeMovie() == null) {
											String details = ((HomeMoviePurchase) purchase).getPurchaseDescription();
											Pattern pattern = Pattern.compile("Movie link for \"([^\"]+)\"");
											Matcher matcher = pattern.matcher(details);
											if (matcher.find()) {
												// Extract the movie title from the matched group
												movieTitle = matcher.group(1);
											}
										} else movieTitle = ((HomeMoviePurchase) purchase).getHomeMovie().getEngtitle();
										DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
										LocalDateTime screeningTime;
										if (((HomeMoviePurchase) purchase).getScreening() == null) {
											String time = ((HomeMoviePurchase) purchase).getPurchaseDescription();
											String dateTimeString = time.split("\nScreening: ")[1];
											screeningTime = LocalDateTime.parse(dateTimeString, formatter1);
										} else
											screeningTime = ((HomeMoviePurchase) purchase).getScreening().getScreeningTime();*/
									//	Duration duration = Duration.between(LocalDateTime.now(), screeningTime);
									Duration duration = Duration.between(LocalDateTime.now(), ((HomeMoviePurchase) purchase).getAvailabilityStartTime());
									DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
									if (duration.toHours() <= 1){ // && movieTitle.equals(movie.getEngtitle())) {
										Notification linkNotification = new Notification(
												"Movie Link", "The link for the movie \"" + ((HomeMoviePurchase) purchase).getMovieTitle() + "\" will be activated soon!" + "\nScreening: " + ((HomeMoviePurchase) purchase).getAvailabilityStartTime().format(formatter1) +
												"\nFor more details check the movie links page in the personal area.",
												LocalDateTime.now(), "Unread", customer);
										int flag = 0;
										for (Notification notification : notifications) {
											if (notification.getCustomer().equals(customer) && notification.getMessage().equals(linkNotification.getMessage()) && notification.getTime().toLocalDate().equals(linkNotification.getTime().toLocalDate())) {
												flag = 1;
												break;
											}}
										if (flag == 0)
											session.save(linkNotification);
									}}}
						}
					List<Notification> finalNotifications = getAllNotifications(session);
					NewMessage newMessage = new NewMessage(finalNotifications, "notificationsList");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
				}
			} else if (msgString.equals("readNotification")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Notification notification = (Notification) message.getObject();
					Notification notification1 = session.get(Notification.class, notification.getId());
					notification1.setStatus("read");
					session.save(notification1);
					List<Notification> notifications = getAllNotifications(session);
					NewMessage message1 = new NewMessage(notifications, "notificationsList");
					client.sendToClient(message1);
					session.getTransaction().commit();
				} catch (Exception e) {
					System.err.println("An error occurred during reading notification.");
                }
            }
		else if (msgString.equals("fetchPurchases")) {
				//int customerId = message.getId();
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Purchase> purchases = getAllPurchases(session);
					NewMessage responseMessage = new NewMessage(purchases, "purchasesResponse");
					client.sendToClient(responseMessage);
				/*	Customer customer = session.get(Customer.class, customerId);
					if (customer != null) {
						List<Purchase> purchases = customer.getPurchaseHistory();
						NewMessage responseMessage = new NewMessage(purchases, "purchasesResponse");
						client.sendToClient(responseMessage);
					} else {
						NewMessage responseMessage = new NewMessage("No customer found with ID: " + customerId, "error");
						client.sendToClient(responseMessage);
					}
				 */
					session.getTransaction().commit();
				} catch (Exception e) {
					System.err.println("An error occurred during fetchPurchases: " + e.getMessage());
				}
			} else if (msgString.equals("returnProduct")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Purchase> purchases = getAllPurchases(session);
					Purchase purchase = (Purchase) message.getObject();
					Purchase purchaseToReturn = session.get(Purchase.class, purchase.getId());
					Customer customer = session.get(Customer.class, purchase.getCustomer().getId());
					Purchase masterPurchase = null;
					List<Purchase> otherPurchases = new ArrayList<>();
					for(Purchase purchase1 : purchases) {
						if (purchase1.getPurchaseDate().equals(purchaseToReturn.getPurchaseDate())
								&& purchase1.getCustomer().getId() == purchaseToReturn.getCustomer().getId()) {
							if(!(purchase1 instanceof Card) && !(purchase1 instanceof HomeMoviePurchase) && !purchase1.getProductType().equals("Movie Ticket")) {
								masterPurchase = purchase1;
							} else {
								otherPurchases.add(purchase1);
							}
						}
					}	if(purchase instanceof HomeMoviePurchase){
						if( ((HomeMoviePurchase) purchase).getScreening() != null) {
							Screening screening = session.get(Screening.class, ((HomeMoviePurchase) purchase).getScreening().getId());
							screening.getHomeMoviePurchases().remove((HomeMoviePurchase) purchaseToReturn);
							session.update(screening);
						}
						if(((HomeMoviePurchase) purchaseToReturn).getHomeMovie() != null) {
							HomeMovie homeMovie = session.get(HomeMovie.class, ((HomeMoviePurchase) purchaseToReturn).getHomeMovie().getId());
							homeMovie.removeHomeMoviePurchase((HomeMoviePurchase) purchaseToReturn);
							session.update(homeMovie);
						}
						session.update(purchaseToReturn);
					}
					if(masterPurchase.getQuantity()==1){
						Purchase purchase3 = session.get(Purchase.class, masterPurchase.getId());
						customer.getPurchaseHistory().remove(purchase3);
						session.save(customer);
						session.remove(purchase3);
						if(purchaseToReturn.getProductType().equals("Movie Ticket")) {
							String[] lines = purchaseToReturn.getPurchaseDescription().split("\n");
							String date = "";
							String time = "";
							String movie = "";
							for (String line : lines) {
								if (line.startsWith("Movie:")){
									movie = line.split(": ")[1].trim();
								}
								if (line.startsWith("Date:")) {
									date = line.split(": ")[1].trim();
								}
								if (line.startsWith("Time:")) {
									time = line.split(": ")[1].trim();
									break;
								}
							}

							List<Screening> screeningsList = getAllScreenings(session); // Assuming this method exists
							LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

							// Parse the time string into a LocalTime
							LocalTime time1 = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
							LocalDateTime dateTime = LocalDateTime.of(date1, time1);

							screeningsList.removeIf(screening -> screening.getBranch()==null);
							screeningsList.removeIf(screening -> !screening.getBranch().getName().equals(purchaseToReturn.getBranchName()));
							for (Screening screen : screeningsList) {
								if (screen.getScreeningTime().equals(dateTime) && screen.getMovie().getEngtitle().equals(movie)){
									screen.setAvailableSeatAt(purchaseToReturn.getSeatNum());
									screen.setAvailableSeats(screen.getAvailableSeats() + 1);
									screen.setTakenSeats(screen.getTakenSeats() - 1);
									break;
								}
								// Save the updated screening
								session.save(screen);
								session.flush();
							}

							List<Screening> screenings = getAllScreenings(session);
							sendToAllClients(new NewMessage(screenings, "screeningTimes"));
						}
					} else if(purchase instanceof Card) {
						Purchase purchase2 = session.get(Purchase.class, masterPurchase.getId());
						purchase2.setQuantity(purchase2.getQuantity() - 1);
						if (purchase2.getQuantity() == 1)
							purchase2.setPurchaseDescription("A cinema cards was ordered containing 20 tickets, which allows access to movie screenings at all our branches based on available seating.");
						else
							purchase2.setPurchaseDescription(purchase2.getQuantity() + " cinema cards were ordered containing 20 tickets each, which allows access to movie screenings at all our branches based on available seating.");
						purchase2.setPricePaid(purchase.getPricePaid() * purchase.getQuantity());
						session.save(purchase2);
				} else if (purchase.getProductType().equals("Movie Ticket")) {
						Purchase purchase2 = session.get(Purchase.class, masterPurchase.getId());
						purchase2.setQuantity(purchase2.getQuantity() - 1);
						session.update(purchase2);

						String[] lines = purchaseToReturn.getPurchaseDescription().split("\n");
						String movie = "";
						String date = "";
						String time = "";
						String seatToDelete = "";
						for (String line : lines) {
							if (line.startsWith("Movie:")) {
								movie = line.split(": ")[1].trim();
							}
							if (line.startsWith("Date:")) {
								date = line.split(": ")[1].trim();
							}
							if (line.startsWith("Time:")) {
								time = line.split(": ")[1].trim();
							}
							if (line.startsWith("Seat ID:")) {
								seatToDelete = line.split(": ")[1].trim();
								break;
							}
						}

						List<String> seats = new ArrayList<>();
						for (Purchase item : otherPurchases) {
							String[] lines2 = item.getPurchaseDescription().split("\n");
							String seat = "";
							for (String line : lines2) {
								if (line.startsWith("Seat ID:")) {
									seat = line.split(": ")[1].trim();
									break;
								}
							}
							if (!seat.equals(seatToDelete))
								seats.add(seat);
						}
						String seatsString = String.join(" ", seats);

						purchase2.setPurchaseDescription("Movie Tickets Details: Order Summary:\nMovie: " + movie +
								"\nDate: " + date + "\nTime: " + time + "\nNumber of tickets: " + purchase2.getQuantity() +
								"\nSeats IDs: " + seatsString +
								"\nBranch: " + masterPurchase.getBranchName());

						purchase2.setPricePaid(purchase2.getPricePaid() - purchaseToReturn.getPricePaid());
						session.save(purchase2);

						List<Screening> screeningsList = getAllScreenings(session); // Assuming this method exists
						LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

						// Parse the time string into a LocalTime
						LocalTime time1 = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
						LocalDateTime dateTime = LocalDateTime.of(date1, time1);

						screeningsList.removeIf(screening -> screening.getBranch()==null);
								screeningsList.removeIf(screening -> !screening.getBranch().getName().equals(purchaseToReturn.getBranchName()));
						for (Screening screen : screeningsList) {
							if (screen.getScreeningTime().equals(dateTime) && screen.getMovie().getEngtitle().equals(movie)){
									screen.setAvailableSeatAt(purchaseToReturn.getSeatNum());
									screen.setAvailableSeats(screen.getAvailableSeats() + 1);
									screen.setTakenSeats(screen.getTakenSeats() - 1);
									break;
							}
							// Save the updated screening
							session.save(screen);
							session.flush();
						}

					List<Screening> screenings = getAllScreenings(session);
					sendToAllClients(new NewMessage(screenings, "screeningTimes"));
					}

					customer.getPurchaseHistory().remove(purchaseToReturn);
					session.save(customer);
					session.remove(purchaseToReturn);
					NewMessage newMessage = new NewMessage("purchaseReturned");
					client.sendToClient(newMessage);
					List<Purchase> finalPurchases = getAllPurchases(session);
					NewMessage newMessage2 = new NewMessage(finalPurchases,"purchasesResponse");
					sendToAllClients(newMessage2);
					List<Card> cards = getAllCards(session);
					NewMessage newMessage3 = new NewMessage(cards,"cardsList");
					sendToAllClients(newMessage3);
					session.getTransaction().commit();
				}catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
				}
			} else if (msgString.equals("processPayment")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Purchase> purchases = (List<Purchase>) message.getObject();
					for (Purchase purchase : purchases) {
						if(purchase instanceof HomeMoviePurchase homeMoviePurchase) {
							List<Screening> screenings = getScreeningsForMovie(session, homeMoviePurchase.getHomeMovie().getId());
							screenings.removeIf(screening -> !screening.getScreeningTime().equals(homeMoviePurchase.getScreening().getScreeningTime()));
							Screening screening = session.get(Screening.class, screenings.get(0).getId());
							homeMoviePurchase.setScreening(screening);
							session.save(homeMoviePurchase);
							screening.getHomeMoviePurchases().add(homeMoviePurchase);
							session.save(screening);
							HomeMovie homeMovie = session.get(HomeMovie.class, homeMoviePurchase.getHomeMovie().getId());
							homeMovie.addHomeMoviePurchase(homeMoviePurchase);
							homeMoviePurchase.setHomeMovie(homeMovie);
							session.save(homeMovie);
						}
						session.save(purchase);
					}
					Customer customer = purchases.get(0).getCustomer();
					Customer savedCustomer = session.get(Customer.class, customer.getId());
					if (savedCustomer == null) {
						session.save(customer);
					} else {
						for (Purchase purchase : purchases) {
							savedCustomer.addPurchase(purchase);
							session.save(savedCustomer);
						}
					}
					List<Purchase> allPurchases = getAllPurchases(session);
					NewMessage responseMessage = new NewMessage(allPurchases, "purchasesResponse");
					sendToAllClients(responseMessage);
					NewMessage newMessage1 = new NewMessage("purchaseSuccessful");
					client.sendToClient(newMessage1);
					// Commit the transaction to persist the changes
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred during the payment process.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("processPaymentForTickets")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Purchase> purchases = (List<Purchase>) message.getObject();

					Customer customer = purchases.get(0).getCustomer();
					Customer savedCustomer = session.get(Customer.class, customer.getId());
					String paymentMethod=purchases.get(0).getPaymentMethod();
					if (paymentMethod.equals("Credit Card")) {
						if (savedCustomer == null) {
							// Customer doesn't exist, save the customer first
							session.save(customer);

							// Associate each purchase with the newly saved customer
							for (Purchase purchase : purchases) {
								customer.addPurchase(purchase);  // Add purchase to the customer
								purchase.setCustomer(customer);  // Ensure purchase is paired with the customer
								session.save(purchase);  // Save the purchase
							}
						} else {
							// Customer exists, associate purchases as before
							for (Purchase purchase : purchases) {
								savedCustomer.addPurchase(purchase);  // Add purchase to the existing customer
								purchase.setCustomer(savedCustomer);  // Ensure purchase is paired with the existing customer
								session.save(purchase);  // Save the purchase
							}
						}
					} 	else if (paymentMethod.equals("Ticket Tab")) {
						int status = 0;
						if (savedCustomer == null) {
							// Customer is not in the database
							NewMessage newMessage1 = new NewMessage("purchaseFailed", "purchaseFailed");
							client.sendToClient(newMessage1);
							session.getTransaction().commit();
							return;
						} else {
							boolean hasTicketTab = false;
							for (Purchase purchase : savedCustomer.getPurchaseHistory()) {
								if (purchase instanceof Card) {
									hasTicketTab = true;

									int remainingTickets = ((Card) purchase).getTickets();
									if (remainingTickets >= purchases.get(0).getQuantity()) {
										((Card) purchase).setTickets(remainingTickets - purchases.get(0).getQuantity());
										status = 1;
										break;
									}
								}
							}
							if (hasTicketTab) {
								if (status == 0) {// the customer has a card but the card does not have enough tickets left-> he cant pay with card
									NewMessage newMessage2 = new NewMessage(/*"purchaseFailed",*/ "notEnoughTicketsInCard");
									client.sendToClient(newMessage2);
									session.getTransaction().commit();
									return;
								}
								for (Purchase purchase : purchases) {
									savedCustomer.addPurchase(purchase);
									purchase.setCustomer(savedCustomer);
									session.save(purchase);
								}
							} else {
								NewMessage newMessage2 = new NewMessage("purchaseFailed");
								client.sendToClient(newMessage2);
								session.getTransaction().commit();
								return;
							}
						}
					}
					List<Purchase> allPurchases = getAllPurchases(session);
					NewMessage responseMessage = new NewMessage(allPurchases, "purchasesResponse");
					sendToAllClients(responseMessage);
					NewMessage newMessage1 = new NewMessage("purchasingTicketsCompleted");
					client.sendToClient(newMessage1);
					List<Screening> screenings = getAllScreenings(session);
					sendToAllClients(new NewMessage(screenings, "screeningTimes"));
					List<Card> cards = getAllCards(session);
					NewMessage newMessage3 = new NewMessage(cards,"cardsList");
					sendToAllClients(newMessage3);
					// Commit the transaction to persist the changes
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred during the payment process.");
					exception.printStackTrace();
				}
			} else if(msgString.equals("screeningHallsRequest")){
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Movie requestedMovie = message.getMovie();
					List<Screening> screenings = session.createQuery(
									"from Screening where movie.id = :movieId", Screening.class)
							.setParameter("movieId", requestedMovie.getId())
							.getResultList();
					for (Screening screening : screenings) {
						LocalDateTime screeningTime = screening.getScreeningTime();
					}
					NewMessage newMessage = new NewMessage(screenings, "screeningHalls");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			}
			else if (msgString.equals("SaveSeatsInHall")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					BookingSeatsReq request = (BookingSeatsReq) message.getObject();  // Cast message to BookingSeatsReq
					// Get all screenings and update the corresponding screening
					List<Screening> screeningsList = getAllScreenings(session);  // Assuming this method exists
					for (Screening screen : screeningsList) {
						if (screen.getId() == request.getScreening().getId()) {
							//	System.out.println("scrn.getId() = " + screen.getId() + ", request.getScreening().getId() = " + request.getScreening().getId());
							// Update seat information for the screening
							for (int i = 0; i < request.getArrSize(); i++) {
								screen.setTakenSeatAt(request.getSeats()[i]);
							}
							screen.setAvailableSeats(screen.getAvailableSeats() - request.getArrSize());
							screen.setTakenSeats(screen.getTakenSeats() + request.getArrSize());
							session.saveOrUpdate(screen);
							// Notify client that the seats are saved
							client.sendToClient(new NewMessage(request, "SeatsSaved"));
							List<Screening> screenings = getAllScreenings(session);
							sendToAllClients(new NewMessage(screenings, "screeningTimes"));
							break;
						}
					}
					session.getTransaction().commit();
				}
				catch (Exception e) {
					// Handle IO exception while sending message to client
					System.err.println("Error while sending message to client.");
					e.printStackTrace();
				}
			}
			else if (msgString.startsWith("UndoSaveSeatsInHall")) {
				try (Session session = sessionFactory.openSession()) { // Open session in a try-with-resources block
					session.beginTransaction();
					BookingSeatsReq request = (BookingSeatsReq) ((NewMessage) msg).getObject(); // Cast message to BookingSeatsReq
					// Get all screenings and find the corresponding screening
					List<Screening> screeningsList = getAllScreenings(session); // Assuming this method exists
					for (Screening screen : screeningsList) {
						if (screen.getId() == request.getScreening().getId()) {

							// Update seat availability for the screening
							for (int i = 0; i < request.getArrSize(); i++) {
								screen.setAvailableSeatAt(request.getSeats()[i]);
							}
							screen.setAvailableSeats(screen.getAvailableSeats() + request.getArrSize());
							screen.setTakenSeats(screen.getTakenSeats() - request.getArrSize());

							// Save the updated screening
							session.save(screen);
							session.flush();

							// Send confirmation message back to client
							try {
							//	client.sendToClient(new NewMessage( request,"SeatsFreed"));
								sendToAllClients(new NewMessage( request,"SeatsFreed"));
							} catch (Exception e) {
								e.printStackTrace();
							}
							List<Screening> screenings = getAllScreenings(session);
							sendToAllClients(new NewMessage(screenings, "screeningTimes"));
							break;
						}
					}
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace(); // Handle exception
				}
			} else if (msgString.equals("fetchHomeMoviePurchases")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Object customerIdObject = message.getId();

					int customerId = (int) customerIdObject;
					List<HomeMoviePurchase> homeMoviePurchases = session.createQuery("FROM HomeMoviePurchase WHERE customer.id = :customerId", HomeMoviePurchase.class)
							.setParameter("customerId", customerId)
							.getResultList();

					NewMessage responseMessage = new NewMessage(homeMoviePurchases, "homeMoviePurchasesResponse");
					client.sendToClient(responseMessage);
					session.getTransaction().commit();

				} catch (Exception e) {
					System.err.println("An error occurred while fetching home movie purchases: " + e.getMessage());
					e.printStackTrace();
				}
			} else if (msgString.equals("generateMonthlyReport")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					int year = message.getYear();
					int month = message.getMonth();

					// Fetch purchases of both links and cards based on productType
					List<Purchase> purchases = getMonthlyPurchases(session, year, month);

					// Generate the monthly report data
					Map<LocalDate, DailyReportData> reportData = generateMonthlyReportData(purchases);

					// Send the report data back to the client
					NewMessage responseMessage = new NewMessage(reportData, "monthlyReport");
					client.sendToClient(responseMessage);

					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("dataForComplaintsReport")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					int month = message.getMonth();
					String branchName = message.getBranchName();
					Map<Integer, Integer> complaintsByDay = getComplaintCountByDayAndBranch(session, month, branchName);
					NewMessage newMessage = new NewMessage(complaintsByDay, "complaintsByMonthAndBranch");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}