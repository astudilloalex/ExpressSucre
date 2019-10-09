package data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
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

	public byte[] getReservation(Reservation reservation) {
		this.fontSize = 20;
		this.document = new PDDocument();
		this.addPageA4();
		try {
			this.pageContentStream = new PDPageContentStream(this.document, this.page);
			this.pageContentStream.beginText();
			this.centerText(this.companyName);
			this.fontSize = 12;
			this.setLeading(1f);
			this.addNewLineText(ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_BusPlate")
					+ reservation.getScheduleBean().getBusBean().getPlate());
			this.addNewLineText(ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_BusNumber")
					+ reservation.getScheduleBean().getBusBean().getDiskNumber());
			this.addNewLineText(ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_Schedule")
					+ reservation.getScheduleBean().getDate());
			this.addNewLineText(ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_IdCard")
					+ reservation.getPersonUserBean().getIdCard());
			this.addNewLineText(ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_Name")
					+ reservation.getPersonUserBean().getFirstName() + " "
					+ reservation.getPersonUserBean().getLastName());
			this.addNewLineText(ResourceBundle.getBundle("/Bundle").getString("ReservationPDF_BusSeat")
					+ reservation.getBusSeatBean().getSeatBean().getNumber() + " "
					+ reservation.getBusSeatBean().getLocation());
			this.pageContentStream.endText();
			this.pageContentStream.close();
			this.outputStream = new ByteArrayOutputStream();
			this.document.save(this.outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				this.document.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.outputStream.toByteArray();
	}
}
