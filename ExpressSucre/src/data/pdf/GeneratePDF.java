package data.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import model.Reservation;

public class GeneratePDF implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fontSize;
	private float marginLeft;
	private float marginRight;
	private float marginTop;
	private float marginBottom;
	private float pageWidth;
	private float pageHeight;
	private float textWidth;
	private float textHeight;
	private String companyName;
	private ByteArrayOutputStream outputStream;
	private PDFont font;
	private PDDocument document;
	private PDPage page;
	private PDPageContentStream pageContentStream;

	// Page configuration
    private static final PDRectangle PAGE_SIZE = PDRectangle.A4;
    private static final float MARGIN = 20;
    private static final boolean IS_LANDSCAPE = true;

    // Font configuration
    private static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE =15;

    // Table configuration
    private static final float ROW_HEIGHT = 25;
    private static final float CELL_MARGIN = 2;
	/**
	 * The constructor allows initializing default variables.
	 */
	public GeneratePDF() {
		this.fontSize = 12;
		this.marginLeft = 25.4f;
		this.marginRight = 25.4f;
		this.marginTop = 25.4f;
		this.marginBottom = 25.4f;
		this.companyName = "Express Sucre";
		this.font = PDType1Font.TIMES_ROMAN;
	}

	// Add an A4 page to the document.
	private void addPageA4() {
		this.page = new PDPage(PDRectangle.A4);
		this.document.addPage(this.page);
		this.pageWidth = this.page.getMediaBox().getWidth();
		this.pageHeight = this.page.getMediaBox().getHeight();
	}

	// Set line spacing.
	private void setLeading(float spaced) throws IOException {
		this.pageContentStream.setLeading(spaced * this.fontSize);
	}

	// Center the text that comes as an argument.
	private void centerText(String text) throws IOException {
		this.calculateTextSize(text);
		this.addNewLineText(text, (this.pageWidth - this.textWidth) / 2,
				this.pageHeight - this.marginTop - this.textHeight);
		this.pageContentStream.newLineAtOffset(-(this.pageWidth - this.textWidth) / 2 + this.marginLeft,
				-this.marginTop);
	}

	// Calculate the height and width of the letter.
	private void calculateTextSize(String text) throws IOException {
		this.textWidth = this.font.getStringWidth(text) / 1000 * this.fontSize;
		this.textHeight = this.font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * this.fontSize;
	}

	// Add a new line of text at the indicated coordinates.
	private void addNewLineText(String text, float x, float y) throws IOException {
		this.pageContentStream.setFont(this.font, this.fontSize);
		this.pageContentStream.newLineAtOffset(x, y);
		this.pageContentStream.showText(text);
	}

	// Add a new line of text in the previous coordinates
	private void addNewLineText(String text) throws IOException {
		this.pageContentStream.setFont(this.font, this.fontSize);
		this.pageContentStream.newLine();
		this.pageContentStream.showText(text);
	}

	private static Table tableReservation(Reservation reservation) {
		List<Column> columns = new ArrayList<Column>();
		columns.add(new Column(400, ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_BusPlate")+reservation.getScheduleBean().getBusBean().getPlate()));
		columns.add(new Column(400, ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_BusNumber")+reservation.getScheduleBean().getBusBean().getDiskNumber()));
		String[][] content= {
				{ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_BusSeat")+reservation.getBusSeatBean().getSeatBean().getNumber()+" "+reservation.getBusSeatBean().getLocation(),ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_Schedule")+reservation.getScheduleBean().getDate()},
				{ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_IdCard")+ reservation.getPersonUserBean().getIdCard(), ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_Name")+ reservation.getPersonUserBean().getFirstName()+" "+reservation.getPersonUserBean().getLastName()},
				{ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_BusStationOrigin")+reservation.getScheduleBean().getRouteBean().getBusStation1().getName(), ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_BusStationDestination")+reservation.getScheduleBean().getRouteBean().getBusStation2().getName()},
				{"", ""},
				{"", ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_Amount")+reservation.getScheduleBean().getRouteBean().getAmount()}
		};
		float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);
		Table table = new TableBuilder()
	            .setCellMargin(CELL_MARGIN)
	            .setColumns(columns)
	            .setContent(content)
	            .setHeight(tableHeight)
	            .setNumberOfRows(content.length)
	            .setRowHeight(ROW_HEIGHT)
	            .setMargin(MARGIN)
	            .setPageSize(PAGE_SIZE)
	            .setLandscape(IS_LANDSCAPE)
	            .setTextFont(TEXT_FONT)
	            .setFontSize(FONT_SIZE)
	            .build();
		return table;
	}
	
	public byte[] getReservation(Reservation reservation) {
		try {
			return new PDFTableGenerator().generatePDF(tableReservation(reservation)).toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
