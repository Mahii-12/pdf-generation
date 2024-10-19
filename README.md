# pdf-generation

* Implemented PDF generation using iTextPdf and Thymeleaf. iTextPdf handles the creation of PDFs programmatically, while Thymeleaf utilizes HTML templates for dynamic content generation. The HTML template serves as a structured layout for the PDF, allowing for easy customization of the document’s appearance and format.

## Technology Stack:

* I chose Java for its robustness and portability. Spring Boot simplifies the development of RESTful APIs, allowing rapid application setup. Maven is used for dependency management and project build automation.

##  Local Storage:

* Generated PDFs are stored locally on the server’s filesystem. Each PDF is uniquely identified to avoid overwriting and ensure retrievability.

## Functionality:

> Users can send a POST request to the REST API with the invoice details.
> The application generates a PDF based on the provided data and stores it locally.
> If the same data is submitted again, the application retrieves the existing PDF instead of generating a new one, optimizing performance.

## Scalability and SOLID Principles:

* SOLID Principles:
> By adhering to SOLID principles, I've designed a system that is modular, maintainable, and easily extensible, laying the foundation for scalability and future enhancements.

## CRUD Operations:
* Implemented functionality to handle incoming requests, generating PDFs based on user input and ensuring seamless retrieval from local storage.

## InvoiceRequest:
------
 - seller: String
 - sellerGstin: String
 - sellerAddress: String
 - buyer: String
 - buyerGstin: String
 - buyerAddress: String
 - items: List<Item>
 > Item:
 - name: String
 - quantity: String
 - rate: double
 - amount: double

## Postman Validation:

* Functional Testing:
  > Utilized Postman for testing REST endpoints, ensuring that the PDF generation and retrieval functionalities worked as expected. Conducted validation to confirm that the application handled various input scenarios correctly, enhancing reliability.

