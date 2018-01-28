-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2018-01-24 20:58:42.069

-- tables
-- Table: Author
CREATE TABLE Author (
    id int  NOT NULL,
    first_name varchar(255)  NOT NULL,
    last_name varchar(255)  NOT NULL,
    CONSTRAINT Author_pk PRIMARY KEY  (id)
);

-- Table: Author_Book
CREATE TABLE Author_Book (
    id int  NOT NULL,
    author_id int  NOT NULL,
    book_isbn varchar(14)  NOT NULL,
    CONSTRAINT Author_Book_pk PRIMARY KEY  (id)
);

-- Table: Book
CREATE TABLE Book (
    isbn varchar(14)  NOT NULL,
    title varchar(255)  NOT NULL,
    author_id int  NOT NULL,
    pub_id int  NOT NULL,
    pub_date timestamp  NOT NULL,
    pages int  NOT NULL,
    genre varchar(255)  NOT NULL,
    list_price decimal(5,2)  NOT NULL,
    sale_price decimal(5,2)  NOT NULL,
    wholesale_price decimal(5,2)  NOT NULL,
    image_name varchar(255)  NOT NULL,
    description varchar(2184)  NOT NULL,
    format varchar(255)  NOT NULL,
    entered_date timestamp  NOT NULL,
    removal_status char(1)  NOT NULL,
    CONSTRAINT Book_pk PRIMARY KEY  (isbn)
);

-- Table: Client
CREATE TABLE Client (
    id int  NOT NULL,
    email int  NOT NULL,
    password varchar(255)  NOT NULL,
    title varchar(10)  NULL,
    first_name varchar(255)  NOT NULL,
    last_name varchar(255)  NOT NULL,
    phone_number varchar(10)  NOT NULL,
    manager int  NOT NULL,
    company_name int  NULL,
    address_1 varchar(255)  NOT NULL,
    address_2 varchar(255)  NULL,
    city varchar(255)  NOT NULL,
    province varchar(255)  NOT NULL,
    country varchar(255)  NOT NULL,
    postal_code varchar(6)  NOT NULL,
    CONSTRAINT Client_pk PRIMARY KEY  (id)
);

-- Table: Invoice
CREATE TABLE Invoice (
    id int  NOT NULL,
    invoice_details_id int  NOT NULL,
    client_id int  NOT NULL,
    date_of_sale timestamp  NOT NULL,
    net_value int  NOT NULL,
    gross_value int  NOT NULL,
    CONSTRAINT Invoice_pk PRIMARY KEY  (id)
);

-- Table: Invoice_Details
CREATE TABLE Invoice_Details (
    id int  NOT NULL,
    sale_number int  NOT NULL,
    isbn varchar(14)  NOT NULL,
    book_price decimal(5,2)  NOT NULL,
    PST_rate int  NOT NULL,
    GST_rate int  NOT NULL,
    HST_rate int  NOT NULL,
    CONSTRAINT Invoice_Details_pk PRIMARY KEY  (id)
);

-- Table: Publisher
CREATE TABLE Publisher (
    id int  NOT NULL,
    name varchar(255)  NOT NULL,
    location varchar(255)  NOT NULL,
    CONSTRAINT Publisher_pk PRIMARY KEY  (id)
);

-- Table: Review
CREATE TABLE Review (
    id int  NOT NULL,
    book_isbn varchar(14)  NOT NULL,
    client_id int  NOT NULL,
    rating int  NOT NULL,
    review_message varchar(2184)  NOT NULL,
    approval_status varchar(50)  NOT NULL,
    review_date timestamp  NOT NULL,
    CONSTRAINT Review_pk PRIMARY KEY  (id)
);

-- Table: Taxes
CREATE TABLE Taxes (
    province varchar(255)  NOT NULL,
    PST_rate decimal(4,2)  NOT NULL,
    GST_rate decimal(4,2)  NOT NULL,
    HST_rate decimal(4,2)  NOT NULL,
    CONSTRAINT Taxes_pk PRIMARY KEY  (province)
);

-- foreign keys
-- Reference: Author_Book_Author (table: Author_Book)
ALTER TABLE Author_Book ADD CONSTRAINT Author_Book_Author
    FOREIGN KEY (author_id)
    REFERENCES Author (id);
	
	
ALTER TABLE Author
ADD CONSTRAINT UQ_AuthorName UNIQUE(first_name, last_name);

-- Reference: Author_Book_Book (table: Author_Book)
ALTER TABLE Author_Book ADD CONSTRAINT Author_Book_Book
    FOREIGN KEY (book_isbn)
    REFERENCES Book (isbn);

-- Reference: Book_Publisher (table: Book)
ALTER TABLE Book ADD CONSTRAINT Book_Publisher
    FOREIGN KEY (pub_id)
    REFERENCES Publisher (id);

-- Reference: Invoice_Client (table: Invoice)
ALTER TABLE Invoice ADD CONSTRAINT Invoice_Client
    FOREIGN KEY (client_id)
    REFERENCES Client (id);

-- Reference: Invoice_Invoice_Details (table: Invoice)
ALTER TABLE Invoice ADD CONSTRAINT Invoice_Invoice_Details
    FOREIGN KEY (invoice_details_id)
    REFERENCES Invoice_Details (id);

-- Reference: Review_Book (table: Review)
ALTER TABLE Review ADD CONSTRAINT Review_Book
    FOREIGN KEY (book_isbn)
    REFERENCES Book (isbn);

-- Reference: Review_Client (table: Review)
ALTER TABLE Review ADD CONSTRAINT Review_Client
    FOREIGN KEY (client_id)
    REFERENCES Client (id);

	
-- End of file.

