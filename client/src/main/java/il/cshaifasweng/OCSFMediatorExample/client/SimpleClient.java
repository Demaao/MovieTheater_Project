package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import org.greenrobot.eventbus.EventBus;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SimpleClient extends AbstractClient {

	public static SimpleClient client = null;

	SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof NewMessage) {
			NewMessage message = (NewMessage) msg;
			Platform.runLater(() -> {
				if (message.getMessage().equals("movies")) {
					List<Movie> movies = (List<Movie>) message.getObject();
					EventBus.getDefault().post(new UpdateMoviesEvent(movies));
				} else if (message.getMessage().equals("soonMovies")) {
					List<SoonMovie> soonMovies = (List<SoonMovie>) message.getObject();
					EventBus.getDefault().post(new UpdateSoonMoviesEvent(soonMovies));
				} else if (message.getMessage().equals("homeMovies")) {  // טיפול בהודעת homeMovies
					List<HomeMovie> homeMovies = (List<HomeMovie>) message.getObject();
					EventBus.getDefault().post(new UpdateHomeMoviesEvent(homeMovies));
				} else if (message.getMessage().equals("login")) {  ///////////////////////////////////////////////////////
					App.loginDeniedCounter = 0;
					Employee employee = (Employee) message.getObject();
					EventBus.getDefault().post(new UpdateLoginEvent(employee));
				} else if (message.getMessage().equals("loginDenied")) {
					App.loginDeniedCounter++;
					EventBus.getDefault().post(new WarningEvent(new Warning("User name or Password is incorrect!")));
				} else if (message.getMessage().equals("Alreadylogin")) {
					App.loginDeniedCounter = 0;
					EventBus.getDefault().post(new WarningEvent(new Warning("User is already logged in!")));
				} else if (message.getMessage().equals("logOut")) {
					EventBus.getDefault().post(new MessageEvent("Log out"));
					EventBus.getDefault().post(new WarningEvent(new Warning("Logged out successfully!")));
				} else if (message.getMessage().equals("screeningTimes")) {
					List<Screening> screenings = (List<Screening>) message.getObject();
					EventBus.getDefault().post(new UpdateScreeningTimesEvent(screenings));
				} else if (message.getMessage().equals("movieRemoved")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Movie removed successfully!")));
				} else if (message.getMessage().equals("movieAdded")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Movie added successfully!")));
				} else if (message.getMessage().equals("screeningAdded")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Screening added successfully!")));
				} else if (message.getMessage().equals("screeningRemoved")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Screening removed successfully!")));
				} else if (message.getMessage().equals("movieNotAvailable")) {
					//EventBus.getDefault().post(new NewMessage(message.getObject(), "movieNotAvailable"));
					EventBus.getDefault().post(message);
				} else if (message.getMessage().equals("screeningUpdated")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Screening time updated successfully!")));
				} else if (message.getMessage().equals("screeningUpdateFailed")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Failed to update screening time.")));
				} else if (message.getMessage().equals("complaintSubmitted")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Complaint submitted successfully!")));
				} else if (message.getMessage().equals("complaints")) {
					List<Complaint> complaints = (List<Complaint>) message.getObject();
					EventBus.getDefault().post(new UpdateComplaintsEvent(complaints));
				} else if (message.getMessage().equals("complaintAnswered")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Complaint answered successfully!")));
				} else if(message.getMessage().equals("requestReceived")){
					EventBus.getDefault().post(new WarningEvent(new Warning("Request sent successfully!")));
				} else if (message.getMessage().equals("requests")) {
					List<ChangePriceRequest> requests = (List<ChangePriceRequest>) message.getObject();
					EventBus.getDefault().post(new UpdateRequestEvent(requests));
				}  else if (message.getMessage().equals("cinema")) {
					//System.out.println("got the message");
					EventBus.getDefault().post(new UpdateCinemaEvent((Cinema)message.getObject()));
				}else if(message.getMessage().equals("requestDenied")){
					EventBus.getDefault().post(new WarningEvent(new Warning("Request denied successfully!")));
				} else if(message.getMessage().equals("requestConfirmed")){
					EventBus.getDefault().post(new WarningEvent(new Warning("Request confirmed successfully!")));
				} else if (message.getMessage().equals("loginCustomer")) {  // ×××¤×× ×××ª×××¨××ª ××§××
					App.loginDeniedCounter = 0;
					Customer customer = (Customer) message.getObject();
					EventBus.getDefault().post(new UpdateLoginCustomerEvent(customer));
				} else if (message.getMessage().equals("loginDeniedCustomer")) {
					App.loginDeniedCounter++;
					EventBus.getDefault().post(new WarningEvent(new Warning("ID number is incorrect!")));
				} else if (message.getMessage().equals("AlreadyloginCustomer")) {
					App.loginDeniedCounter = 0;
					EventBus.getDefault().post(new WarningEvent(new Warning("Customer is already logged in!")));
				}  else if (message.getMessage().equals("cardsList")) {
					List<Card> cards = (List<Card>) message.getObject();
					EventBus.getDefault().post(new UpdateCardsEvent(cards));
				} else if (message.getMessage().equals("purchaseSuccessful")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Payment completed successfully!")));
				} else if (message.getMessage().equals("notificationsList")) {
					List<Notification> notifications = (List<Notification>) message.getObject();
					EventBus.getDefault().post(new UpdatePersonalMessageEvent(notifications));
				} else if (message.getMessage().equals("purchasesResponse")) {
					List<Purchase> purchases = (List<Purchase>) message.getObject();
					EventBus.getDefault().post(new UpdatePurchasesEvent(purchases));
				} else if (message.getMessage().equals("purchasesResponse")) {
                List<Purchase> purchases = (List<Purchase>) message.getObject();
                EventBus.getDefault().post(new UpdatePurchasesEvent(purchases));
				} else if (message.getMessage().equals("purchaseReturned")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Purchase refunded successfully!")));
				} else if (message.getMessage().equals("screeningHalls")) {
					List<Screening> screenings = (List<Screening>) message.getObject();
					EventBus.getDefault().post(new UpdateScreeningTimesEvent(screenings));
				} else if (message.getMessage().equals("SeatsSaved")) {
					PaymentTickets.setRequest((BookingSeatsReq) ((NewMessage) msg).getObject());
					App.switchScreen("PaymentTickets");
				} else if (message.getMessage().equals("SeatsFreed")) {
				//	if(PaymentTickets.paymentCanceledFlag == 1)
						//App.switchScreen("MoviesPage");
				} else if (message.getMessage().equals("homeMoviePurchasesResponse")) {
					List<HomeMoviePurchase> homeMoviePurchases = (List<HomeMoviePurchase>) message.getObject();
					EventBus.getDefault().post(new UpdateHomeMoviePurchasesEvent(homeMoviePurchases));
				} else if (message.getMessage().equals("monthlyReport")) {
					// Retrieve the report data from the message
					Map<LocalDate, DailyReportData> reportData = (Map<LocalDate, DailyReportData>) message.getObject();
					// Post the event to EventBus so the client UI can handle the data
					UpdateMonthlyReportEvent event = new UpdateMonthlyReportEvent(reportData);
					EventBus.getDefault().post(event);
				} else if (message.getMessage().equals("complaintsByMonthAndBranch")) {
					Map<Integer, Integer> complaintsByDay = (Map<Integer, Integer>) message.getObject();
					EventBus.getDefault().post(new UpdateComplaintReportEvent(complaintsByDay));
				} 	else if (message.getMessage().equals("purchaseFailed")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("You haven't bought a Card yet in order to use Ticket tab as a payment method!\n\nIn order to complete your"+
							" purchase, you can either buy a Card first, or pay by Credit Card.")));
				} else if (message.getMessage().equals("notEnoughTicketsInCard")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("You don't have enough tickets in your ticket tab!\n\nYou can either pay with Credit Card, or buy another Card.")));
				} else if (message.getMessage().equals("purchasingTicketsCompleted")) {
					EventBus.getDefault().post(new UpdateTicketsPurchasesEvent()); // Custom event
				}
			});
		}
	}

	// Singleton pattern to get the client instance
	public static SimpleClient getClient() {
		if (client == null) {
			//client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
}

