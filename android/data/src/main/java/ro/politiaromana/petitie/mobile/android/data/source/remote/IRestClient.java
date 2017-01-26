package ro.politiaromana.petitie.mobile.android.data.source.remote;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ro.politiaromana.petitie.mobile.android.data.model.DataCounty;
import ro.politiaromana.petitie.mobile.android.data.model.DataTicket;
import ro.politiaromana.petitie.mobile.android.data.model.DataToken;
import ro.politiaromana.petitie.mobile.android.data.model.DataUser;
import rx.Observable;

/**
 * Created by andrei.
 */

public interface IRestClient {

    //auth
    @GET("/auth/users")
    Observable<DataToken> getUserToken(@Query("email") String email,
                                       @Query("password") String password);

    @GET("/auth/devices")
    Observable<DataToken> getDeviceToken(@Query("deviceId") String deviceId);

    //counties
    @GET("/counties")
    Observable<List<DataCounty>> getCounties();

    //tickets
    @GET("/tickets/{ticketId}")
    Observable<DataTicket> getTickets(@Path("ticketId") String ticketId);

    @PUT("/tickets/{ticketId}")
    Observable<DataTicket> updateTicket(@Path("ticketId") String ticketId);

    @DELETE("/tickets/{ticketId}")
    Observable<Void> deleteTicket(@Path("ticketId") String ticketId);

    @GET("/tickets")
    Observable<List<DataTicket>> getAllTickets();

    @POST("/tickets")
    Observable<Void> createTicket(@Body DataTicket dataTicket);

    @GET("/tickets/{ticketId}/messages/{messageId}")
    Observable<DataTicket.Message> getMessage(@Path("ticketId") String ticketId,
                                              @Path("messageId") String messageId);

    @PUT("/tickets/{ticketId}/messages/{messageId}")
    Observable<Void> updateTicketMessage(@Path("ticketId") String ticketId,
                                         @Path("messageId") String messageId,
                                         @Body DataTicket.Message message);
    @DELETE("/tickets/{ticketId}/messages/{messageId}")
    Observable<Void> deleteTicketMessage(@Path("ticketId") String ticketId,
                                         @Path("messageId") String messageId);

    @GET("/tickets/{ticketId}/messages")
    Observable<List<DataTicket.Message>> getMessages(@Path("ticketId") String ticketId);

    @POST("/tickets/{ticketId}/messages")
    Observable<DataTicket.Message> createNewTicketMessage(@Path("ticketId") String ticketId,
                                                          @Body DataTicket.Message message);

    @GET("/tickets/{ticketId}/attachments/{attachmentId}")
    Observable<DataTicket.Attachment> getTicketAttachment(@Path("ticketId") String ticketId,
                                                          @Path("attachmentId") String attachmentId);

    @DELETE("/tickets/{ticketId}/attachments/{attachmentId}")
    Observable<Void> deleteTicketAttachment(@Path("ticketId") String ticketId,
                                            @Path("attachmentId") String attachmentId);

    @GET("/tickets/{ticketId}/attachments")
    Observable<List<DataTicket.Attachment>> getTicketAttachments(@Path("ticketId") String ticketId);

    //@POST("/tickets/{ticketId}/attachments")
    //TODO See how we could use retrofit to send files...or use an IntentService?

    //users
    @GET("/users/{userId}")
    Observable<DataUser> getUser(@Path("userId") String userId);

    @PUT("/users/{userId}")
    Observable<DataUser> updateUser(@Path("userId") String userId);

    @DELETE("/users/{userId}")
    Observable<Void> deleteUser(@Path("userId") String userId);

    @GET("/users")
    Observable<List<DataUser>> getUsers();

    @POST("/users")
    Observable<DataUser> createUser(@Body DataUser user);

    @GET("/users/{userId}/tickets")
    Observable<List<DataTicket>> getUserTickets(@Path("userId") String userId);
}
