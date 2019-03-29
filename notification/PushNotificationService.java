import java.util.List;

import com.gings.domain.PushNotification;

/**
 * 
 * 
 * 일반적으로 푸시 알림은 하나의 기능일 뿐이나 로그아웃 유저의 로그인, connection lost된 유저의 reconnect시에 
 * 새 푸시 알림이 도착함을 알리려면 DB에 푸시 알림 정보를 기록하거나 정보를 처리(읽음 처리 등)해야한다.<br>
 * (모바일 기기만 대상으로 서비스 하겠다면 읽음 처리할 필요 없이 서버에 저장되어있는 푸시 알림(아직 읽지 않은 푸시 알림만
 * 저장)을 삭제하기만하고 기존 읽은 푸시알림은 모바일 기기의 내부디비 등의 저장하는 방식이 가능하나 후에 웹 브라우저까지 
 * 클라이언트 대상으로 두게 될 경우 기존 알림 정보까지 서버에 저장하여야 서로 다른 클라이언트 어플리케이션끼리의 푸시 알림
 * 동기화가 가능.)<br>
 * <br>
 * 이런 로직을 수행하기 위해 {@link com.gings.domain.PushNotification}을 정의하였으며 이 클래스 관련 처리 로직을
 * 수행하기 위해 아래 {@link PushNotificationService}를 정의하게 되었다.<br>
 * 
 * push 알림은 어플리케이션에서 주로 관심을 두는 도메인(상품, 마켓, 게시글, 유저 등)은 분명 아니라고 할 수 있지만 
 * push 알림 기능이라는 비즈니스 로직 측면에서는 의미가 있는 기능이기 때문에 아래와 같이 push 알림을 담당하는 서비스 
 * 클래스를 만들어도 된다고 생각함. 
 * <br>
 * 
 * 
 */
public interface PushNotificationService {
    
    /**
     * @param olderId - 기준이 되는 push notification id.
     * 
     * @return - olderId보다 더 나중에 생성된 push notification의 list.
     */
    public List<PushNotification> getNewerPushNotifications(int olderId, int userId);
    
    /**
     * @param notificationIds - 현재 깅스 어플리케이션의 notification 확인(읽음)처리는 기획적인 
     *                          이유로 사용자가 각각의 notification을 클릭해야 가능하다. 
     *                          위의 이유로 notification 확인은 각각 하나의 {@code notificationId}에 
     *                          대하여 수행된다.
     *                          
     * @param userId - 잘못된 요청이 notification의 상태를 변경시킬 수 있다. 
     *                 그러므로 예를 들어, RDB의 WHERE 절과 같은 검색 조건을 수행시 {@code notificationId}
     *                 뿐 아니라 {@code userId}를 전달되어야 한다.              
     */
    public void confirmNotifications(int notificationId, int userId);
}