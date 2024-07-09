package Services;

import Entites.CommandeDetailsEntity;
import Entites.ProduitDetailsEntity;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PDFExporter {

    public static void exportCommandeDetailsToPDF(CommandeDetailsEntity commandeDetails) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Header
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 750);
            contentStream.showText("Détails de la Commande");
            contentStream.newLine();
            contentStream.endText();

            // Commande Details
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 720);
            contentStream.showText("ID Commande: " + commandeDetails.getIdCommande());
            contentStream.newLine();
            contentStream.showText("Nom Client: " + commandeDetails.getNomClient());
            contentStream.newLine();
            contentStream.showText("Email Client: " + commandeDetails.getEmailClient());
            contentStream.newLine();
            contentStream.showText("Num Tel Client: " + commandeDetails.getNumTelClient());
            contentStream.newLine();
            contentStream.showText("Total Commande: " + commandeDetails.getTotalCommande());
            contentStream.newLine();
            contentStream.showText("Etat Commande: " + commandeDetails.getEtatCommande());
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("Produits:");
            contentStream.newLine();
            contentStream.endText();

            // Produits
            List<ProduitDetailsEntity> produits = commandeDetails.getProduits();
            float yPosition = 520; // Initial y position for product details

            for (ProduitDetailsEntity produit : produits) {
                // Product Details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(25, yPosition);
                contentStream.showText("Nom Produit: " + produit.getNomProduit());
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("Prix: " + produit.getPrixProduit());
                contentStream.newLine();
                contentStream.showText("Quantité: " + produit.getQuantiteProduit());
                contentStream.newLine();
                contentStream.endText();

                yPosition -= 40; // Adjust y position for the image

                // Image
                try {
                    String imagePath = produit.getImagProduit().replace("file:/", "").replace("%20", " ");
                    File imageFile = new File(imagePath);
                    System.out.println(imageFile);
                    if (imageFile.exists()) {
                        PDImageXObject pdImage = PDImageXObject.createFromFile(imageFile.getAbsolutePath(), document);
                        contentStream.drawImage(pdImage, 25, yPosition - 100, 100, 100); // Adjust positioning as needed
                        yPosition -= 160; // Adjust y position for next product
                    } else {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(25, yPosition);
                        contentStream.showText("Image non disponible.");
                        contentStream.newLine();
                        contentStream.endText();
                        yPosition -= 20; // Adjust y position for next product
                    }
                } catch (IOException e) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(25, yPosition);
                    contentStream.showText("Erreur de chargement de l'image.");
                    contentStream.newLine();
                    contentStream.endText();
                    yPosition -= 20; // Adjust y position for next product
                }

                yPosition -= 20; // Adjust y position for next product
            }
        }

        document.save("commande_details.pdf");
        document.close();
    }
}
