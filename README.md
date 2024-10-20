# pdf-generation

* Implemented PDF generation using iTextPdf and Thymeleaf. iTextPdf handles the creation of PDFs programmatically, while Thymeleaf utilizes HTML templates for dynamic content generation. The HTML template serves as a structured layout for the PDF, allowing for easy customization of the document’s appearance and format.

## Technology Stack:

* I chose Java for its robustness and portability. Spring Boot simplifies the development of RESTful APIs, allowing rapid application setup. Maven is used for dependency management and project build automation.

# Application Setup Instructions:

> 1. Clone Repository:
  Clone the repository to your local machine:
 * git clone https://github.com/Mahii-12/pdf-generation.git
> 2. Build the project using Maven:
 * mvn clean install


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

## Testing:

* JUnit and Mockito:
  > Tested the controller endpoints using JUnit and Mockito, where JUnit is a widely-used testing framework for Java applications, while Mockito is a popular mocking framework. Together, they allow for thorough testing of controller methods and REST APIs, ensuring correct behavior and identifying potential issues early in the development cycle.

## Postman Validation:

* Functional Testing:
  > Used Postman to enables functional testing of REST APIs by sending requests and validating responses. This ensures that endpoints function correctly and meet specified requirements, enhancing system reliability and user confidence.


