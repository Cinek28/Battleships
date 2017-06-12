package Model.NetworkInterface;

/** Class for generating network messages */
 
public class GameEvent {

    /** Network messages types: */

	/** Client logging */
	public static final int C_LOGIN = 1001;

	/** Failed login */
	public static final int S_LOGIN_FAIL = 1002;

	/** Broadcast successful login */
	public static final int SB_LOGIN = 1003;

	/** Logout attempt */
	public static final int C_LOGOUT = 1004;

	/** Broadcast logout */
	public static final int SB_LOGOUT = 1005;

	/** Max players in game */
	public static final int S_TOO_MANY_CONNECTIONS = 1006;
	
	/** ID exists */
	public static final int S_USER_EXIST = 1007;

	/** You can join the game */
	public static final int SB_CAN_JOIN_GAME = 1101;

	/** You can't join the game */
	public static final int SB_CANNOT_JOIN_GAME = 1102;

	/** Join attempt */
	public static final int C_JOIN_GAME = 1103;

	/** Successful join attempt */
	public static final int S_JOIN_GAME_OK = 1104;

	/** Client not joined */
	public static final int S_JOIN_GAME_FAIL = 1105;

	/** Broadcast join */
	public static final int SB_PLAYER_JOINED = 1106;

	/** Start broadcast */
	public static final int SB_START_GAME = 1107;

	/** Ending game- client */
	public static final int C_QUIT_GAME = 1108;

	/** Broadcast game ended */
	public static final int SB_PLAYER_QUIT = 1109;

	/** Ready for the game */
	public static final int C_READY = 1110;

	/** Everyone ready */
	public static final int SB_ALL_READY = 1111;

	/** Sending chat message */
	public static final int C_CHAT_MSG = 1201;

	/** Server chat message */
	public static final int SB_CHAT_MSG = 1202;

	/** Client shooting */
	public static final int C_SHOT = 1301;

	/** Server bradcasting the shot */
	public static final int SB_SHOT = 1302;

	/** Shot result */
	public static final int C_SHOT_RESULT = 1304;

	/** Broadcast shot result */
	public static final int SB_SHOT_RESULT = 1305;	
	
	/** Client loses*/
	public static final int C_DEAD = 1308;

	/** Broadcasting lost client*/
	public static final int SB_DEAD = 1309;

	/**  Client loses game */
	public static final int C_PLAYER_DEAD = 1310;

	/**  Server broadcasting game over*/
	public static final int SB_GAME_OVER = 1311;

	private int eventType;

	/** Player sending message */
	private String playerId = "";

	/** Message */
	private String message;

	public GameEvent() {

	}

	public GameEvent(int type) {
		setType(type);
	}

	public GameEvent(int type, String message) {
		this(type);
		this.message = message;
	}

	public GameEvent(String receivedMessage) {
		String x = receivedMessage;
		int idx1 = x.indexOf('|');
		int idx2 = x.indexOf('|', idx1 + 1);
		String a = x.substring(0, idx1);
		String b = x.substring(idx1 + 1, idx2);
		String c = x.substring(idx2 + 1);
		try {
			setType(Integer.parseInt(a));
		} catch (NumberFormatException ex) {
			setType(-1);
		}
		setPlayerId(b);
		setMessage(c);
	}

	public String toSend() {
		String toSend = eventType + "|" + playerId + "|" + getMessage();
		return toSend;
	}

	public void setType(int type) {
		eventType = type;
	}

	public int getType() {
		return eventType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String id) {
		playerId = id;
	}
}
