# PowerPoint to Markdown Converter

This application converts PowerPoint presentations (PPTX files) into Markdown format, allowing you to easily share the content of your slides in text-based formats or view them in a web browser. The converter supports text, images, and formulas.

## Features

- Convert PowerPoint presentations to Markdown format
- Convert PowerPoint presentations to HTML format
- Preview the HTML output within the application
- Export the Markdown or HTML output to PDF or Word (DOCX) files
- Customize font for the output

## Installation

To install the application, simply clone this repository and build the project using your preferred Java IDE. The application is built using JavaFX for the user interface.

### Requirements

- Java 8 or higher
- JavaFX 8 or higher
- Gradle (for building the project)

### Dependencies

The following libraries are used in the project:

- Apache POI: For reading PowerPoint files
- OpenHTMLToPDF: For exporting HTML to PDF
- Jsoup: For parsing and cleaning HTML content
- MathJax: For rendering mathematical formulas in HTML

These dependencies will be automatically downloaded and included in your project when you build it using Gradle.

## Usage

To use the application, follow these steps:

1. Launch the application.
2. Click the "Choose File" button to select a PowerPoint (PPTX) file.
3. The application will automatically convert the PowerPoint content into Markdown and HTML format, and display the Markdown output in a text area and the HTML output in a web view.
4. Optionally, select a font from the font dropdown menu to apply it to the output.
5. To export the Markdown or HTML output, click the "Export" button below the corresponding output pane, and choose either "Export to PDF" or "Export to Word". A file dialog will appear, allowing you to choose the location and file name for the exported file.

## Contributing

Feel free to fork this repository and submit pull requests for new features or bug fixes. Please ensure that your changes are well-documented and follow the existing code style.

## Support

If you encounter any issues or have any questions, please open an issue on GitHub, and we'll be happy to help.
