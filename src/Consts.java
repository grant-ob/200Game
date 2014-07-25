
public class Consts {
	
	private Consts(){}
	
	public static final int NUM_PLAYERS = 4;

	public static final int CARD_HEIGHT = 97;
	public static final int CARD_WIDTH = 73;
	
	//The dimensions of the main frame
	static public final int FRAME_X = 0;
	static public final int FRAME_Y = 0;
	static public final int FRAME_WIDTH = 850;
	static public final int FRAME_HEIGHT = 650;
	
	public static final int gameAreaHeight = FRAME_HEIGHT;
	public static final int gameAreaWidth = FRAME_WIDTH - 350;
	public static final int cardAreaHeight = FRAME_HEIGHT - 60;
	
	public static final int CONSOLE_AREA_WIDTH = FRAME_WIDTH - gameAreaWidth;
	public static final int CONSOLE_AREA_HEIGHT = FRAME_HEIGHT - 400;
	public static final int SCROLL_PANE_WIDTH = 320;
	public static final int SCROLL_PANE_HEIGHT = 150;
	
	public static final int STATUS_AREA_WIDTH = FRAME_WIDTH - gameAreaWidth;
	public static final int STATUS_AREA_HEIGHT = FRAME_HEIGHT - CONSOLE_AREA_HEIGHT;
	
	public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";


}
