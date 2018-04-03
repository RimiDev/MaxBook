USE BookStore_DB;
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS Ads;
DROP TABLE IF EXISTS Survey;
DROP TABLE IF EXISTS Invoice_Details;
DROP TABLE IF EXISTS Invoice;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Author_Book;
DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Taxes;
DROP TABLE IF EXISTS Publisher;
DROP TABLE IF EXISTS Author;
SET foreign_key_checks = 1;

CREATE TABLE Author (
    id int PRIMARY KEY auto_increment,
    first_name varchar(255),
    last_name varchar(255) NOT NULL
);

INSERT INTO Author (first_name,last_name) VALUES
("Rupi", "Kaur"),
("R. J.", "Palacio"),
("Giles", "Andreae"),
("Mark", "Manson"),
("Jef", "Kinney"),
("Laurel", "Randolp"),
("J. D.", "Vance"),
("Neil deGrasse", "Tyson"),
("Roger", "Priddy"),
("Melissa", "Hartwig"),
("Margaret", "Atwood"),
("Gary", "Chapman"),
("Jen", "Sincero"),
("Hilary", "Clinton"),
(NULL,"Dr. Seuss"),
("William H.", "McRaven"),
("George", "Orwell"),
("Dale", "Carnegie"),
("Dan", "Brown"),
("Dav", "Pilkey"),
("Martha Hall", "Kelly"),
("Rod", "Campbell"),
("Don Miguel", "Ruiz"),
("Sandra", "Boynton"),
("Tom", "Rath"),
("Emily Winfield", "Martin"),
("William P.", "Young"),
("Marie", "Kondo"),
("Sheryl", "Sandberg"),
("Adam", "Grant"),
("Thug", "Kitchen"),
("Pete", "Souza"),
(NULL, "American Psychological Association"), 
("Amor", "Towles"),
("Margaret Wise", "Brown"), 
("Clement", "Hurd"),
("Jay", "Asher"),
("Walter", "Isaacson"),
("Deborah", "Diesen"),
("Ann Whitford", "Paul"),
("Bill", "Martin Jr."),
("Wizards RPG Team",""),
("Andrea", "Beaty"),
("Bill", "O'Reilly"),
("Adam", "Rubin"),
("Dave", "Ramsey"),
("JJ", "Smith"),
(NULL, "Scholastic"),
("Amy", "Ramos"),
("Brene", "Brown"),
("Ray", "Dalio"),
("Al", "Franken"),
("J. K.", "Rowling"),
("Eric", "Carle"),
("Golden", "Books"),
("Rob", "Elliott"),
("Timothy", "Ferriss"),
("Adam", "Gasiewski"),
("Emily", "Beck"),
("Rick", "Riordan"),
("Nicola", "Yoon"),
("Simon", "Sinek"),
("Neil", "Gaiman"),
("Mark", "Sullivan"),
("Lysa", "TerKeurst"),
("Valorie Lee", "Schaefer"),
("Jeannette", "Walls"),
("F. Scott", "Fitzgerald"),
("Daniel", "Kahneman"),
("Ernest", "Cline"),
("Steven R.", "Covey"),
("Oprah", "Winfrey"),
("Random", "House"),
("Shel", "Silverstein"),
("Chelsea", "Clinton"),
("Timothy", "Snyder"),
("Paulo", "Coelho"),
("Ray", "Bradbury"),
("John", "Grisham"),
("John", "Green"),
("Robert", "Munsch"),
("Ree", "Drummond"),
("Carol S.", "Dweck"),
("Karen", "Katz"),
("Ron", "Chernow"),
("Yuval Noah", "Harari"),
("Benjamin", "Graham"),
("Dr. Steven R", "Gundry M.D"),
("David", "Grann"),
("Paula", "Hawkins"),
("Jack", "Thorne"),
("Jeff", "Kinney"),
("Lin-Manuel", "Miranda"),
("Michael", "Wolff");

CREATE TABLE Publisher (
    id int PRIMARY KEY auto_increment,
    name varchar(255) NOT NULL
);

INSERT INTO PUBLISHER (name) VALUES
("Andrews McMeel Publishing"),
("Knopf Books for Young Readers"),
("Cartwheel Books"),
("HarperOne"),
("Harry N. Abrams"),
("Rockridge Press"),
("Harper"),
("W. W. Norton & Company"),
("Priddy Books"),
("Houghton Mifflin Harcourt"),
("Anchor"),
("Northfield Publishing"),
("Running Press Adult"),
("Simon & Schuster"),
("Random House"),
("Grand Central Publishing"),
("Signet Classic"),
("Pocket Books"),
("Doubleday"),
("Graphix"),
("Ballantine Books"),
("Little Simon"),
("Amber-Allen Publishing"),
("Gallup Press"),
("Random House Books for Young Readers"),
("Windblown Media"),
("Ten Speed Press"),
("Knopf"),
("Rodale books"),
("Little, Brown and Company"),
("American Psychological Association"),
("Viking"),
("HarperFestival"),
("Razorbill"),
("Farrar, Straus and Giroux"),
("Henry Holt and Co."),
("Wizards of the Coast"),
("Dial Books"),
("Thomas Nelson"),
("Atria Books"),
("Scholastic Inc."),
("Avery"),
("Twelve"),
("Disney-Hyperion"),
("Arthur A. Levine Books"),
("Philomel Books"),
("Golden Books"),
("Revell"),
("Independent"),
("Ember"),
("Portfolio"),
("Lake Union Publishing"),
("Amer Girl"),
("Scribner"),
("Broadway Books"),
("Flatiron Books"),
("Harper & Row"),
("Tim Duggan Books"),
("Dutton Books for Young Readers"),
("Hazelden Publishing"),
("Firefly Books"),
("William Morrow Cookbooks"),
("Penguin Press"),
("HarperBusiness"),
("Harper Wave"),
("Riverhead Books"),
("Pending");

CREATE TABLE Taxes (
    province varchar(255) PRIMARY KEY,
    PST_rate numeric(4,2) NOT NULL,
    GST_rate numeric(4,2) NOT NULL,
    HST_rate numeric(4,2) NOT NULL
);

INSERT INTO TAXES (province, PST_rate, GST_rate, HST_rate) VALUES 
('Alberta', 0.00, 0.05, 0.05),
('British Columbia', 0.07, 0.05, 0.12),
('Manitoba', 0.08, 0.05, 0.13),
('New-Brunswick', 0.10, 0.05, 0.15),
('Newfoundland and Labrador', 0.10, 0.05, 0.15),
('Northwest Territories', 0.00, 0.05, 0.05),
('Nova Scotia', 0.10, 0.05, 0.15),
('Nunavut', 0.00, 0.05, 0.05),
('Ontario', 0.08, 0.05, 0.13),
('Quebec', 0.10, 0.05, 0.15),
('Saskatchewan', 0.06, 0.05, 0.11),
('Yukon', 0.00, 0.05, 0.05);

CREATE TABLE Book (
    isbn varchar(14) PRIMARY KEY,
    title varchar(255) NOT NULL,
    publisher_id int NOT NULL,
    publish_date timestamp NOT NULL,
    pages int NOT NULL,
    genre varchar(255) NOT NULL,
    list_price numeric(5,2) NOT NULL,
    sale_price numeric(5,2) NOT NULL,
    wholesale_price numeric(5,2) NOT NULL,
    format varchar(255) NOT NULL,
    entered_date timestamp DEFAULT CURRENT_TIMESTAMP,
    removal_status char(1) NOT NULL,
    description varchar(2184) NOT NULL,
    FOREIGN KEY (publisher_id) REFERENCES Publisher(id)
);

INSERT INTO book VALUES ('978-1449474256','Milk and Honey',1,'2015-10-06 00:00:00',208,'Poetry',14.99,8.99,7.19,'.png','2018-01-01 00:00:00',0,'The book is divided into four chapters, and each chapter serves a different purpose. Deals with a different pain. Heals a different heartache. Milk and Honey takes readers through a journey of the most bitter moments in life and finds sweetness in them because there is sweetness everywhere if you are just willing to look.');
INSERT INTO book VALUES ('978-0375869020','Wonder',2,'2012-02-14 00:00:00',320,'Children',16.99,10.69,8.55,'.png','2018-01-01 00:00:00',0,'August Pullman was born with a facial difference that, up until now, has prevented him from going to a mainstream school. Starting 5th grade at Beecher Prep, he wants nothing more than to be treated as an ordinary kidâ€�?but his new classmates canâ€™t get past Auggieâ€™s extraordinary face. WONDER, now a #1 New York Times bestseller and included on the Texas Bluebonnet Award master list, begins from Auggieâ€™s point of view, but soon switches to include his classmates, his sister, her boyfriend, and others. These perspectives converge in a portrait of one communityâ€™s struggle with empathy, compassion, and acceptance.');
INSERT INTO book VALUES ('978-0545392556',"Giraffes Can't Dance",3,'2012-03-01 00:00:00',32,'Children',6.99,5.06,4.05,'.png','2018-01-01 00:00:00',0,"Giraffes Can't Dance is a touching tale of Gerald the giraffe, who wants nothing more than to dance. With crooked knees and thin legs, it's harder for a giraffe than you would think. Gerald is finally able to dance to his own tune when he gets some encouraging words from an unlikely friend.");
INSERT INTO book VALUES ('978-0062457714','The Subtle Art of Not Giving a F*ck: A Counterintuitive Approach to Living a Good Life',4,'2016-09-13 00:00:00',224,'Self-Help',24.99,14.99,11.99,'.png','2018-01-01 00:00:00',0,"In this generation-defining self-help guide, a superstar blogger cuts through the crap to show us how to stop trying to be 'positive' all the time so that we can truly become better, happier people.");
INSERT INTO book VALUES ('978-1419725456','The Getaway',5,'2017-11-07 00:00:00',224,'Children',19.95,9.59,7.67,'.png','2018-01-01 00:00:00',0,'With light-footed rhymes and high-stepping illustrations, this tale is gentle inspiration for every child with dreams of greatness.');
INSERT INTO book VALUES ('978-1623156121','Electric Pressure Cooker Cookbook: Easy Recipes for Fast & Healthy Meals',6,'2016-04-06 00:00:00',176,'Cookbook',14.99,7.04,5.63,'.png','2018-01-01 00:00:00',0,'Thereâ€™s nothing the Instant Pot canâ€™t doâ€•and with the right cookbook in hand, thereâ€™s nothing you canâ€™t cook. The Instant PotÂ® Electric Pressure Cooker Cookbook will teach you to create a variety of healthy, easy-to-make recipes with confidence. From savory breakfasts and hearty stews to decadent desserts and more, this Instant PotÂ® cookbook is sure to satisfy everyone at the table.');
INSERT INTO book VALUES ('978-0062300546','Hillbilly Elegy: A Memoir of a Family and Culture in Crisis',7,'2016-06-28 00:00:00',272,'Politics',27.99,15.10,12.08,'.png','2018-01-01 00:00:00',0,'Hillbilly Elegy is a passionate and personal analysis of a culture in crisisâ€�?that of white working-class Americans. The decline of this group, a demographic of our country that has been slowly disintegrating over forty years, has been reported on with growing frequency and alarm, but has never before been written about as searingly from the inside. J. D. Vance tells the true story of what a social, regional, and class decline feels like when you were born with it hung around your neck.');
INSERT INTO book VALUES ('978-0393609394','Astrophysics for People in a Hurry',8,'2017-05-02 00:00:00',224,'Science',18.95,11.37,9.10,'.png','2018-01-01 00:00:00',0,'What is the nature of space and time? How do we fit within the universe? How does the universe fit within us? Thereâ€™s no better guide through these mind-expanding questions than acclaimed astrophysicist and best-selling author Neil deGrasse Tyson.');
INSERT INTO book VALUES ('978-0312510787','First 100 Words',9,'2011-05-10 00:00:00',26,'Children',5.99,4.34,3.47,'.png','2018-01-01 00:00:00',0,'Your little one will soon learn some essential first words and pictures with this bright board book. There are 100 color photographs to look at and talk about, and 100 simple first words to read and learn, too. The pages are made from tough board for hours of fun reading, and the cover is softly padded for little hands to hold.');
INSERT INTO book VALUES ('978-0544609716','The Whole30: The 30-Day Guide to Total Health and Food Freedom',10,'2015-04-21 00:00:00',432,'Health & Fitness',30.00,21.49,17.19,'.png','2018-01-01 00:00:00',0,'Millions of people visit Whole30.com every month and share their stories of weight loss and lifestyle makeovers. Hundreds of thousands of them have read It Starts With Food, which explains the science behind the program. At last, The Whole30 provides the step-by-step, recipe-by-recipe guidebook that will allow millions of people to experience the transformation of their entire life in just one month.');
INSERT INTO book VALUES ('978-0385490818',"The Handmaid's Tale",11,'1998-03-16 00:00:00',311,'Fiction',15.95,9.57,7.66,'.png','2018-01-01 00:00:00',0,'Offred is a Handmaid in the Republic of Gilead. She may leave the home of the Commander and his wife once a day to walk to food markets whose signs are now pictures instead of words because women are no longer allowed to read. She must lie on her back once a month and pray that the Commander makes her pregnant, because in an age of declining births, Offred and the other Handmaids are valued only if their ovaries are viable.');
INSERT INTO book VALUES ('978-0802412706','The 5 Love Languages: The Secret to Love that Lasts',12,'2015-01-01 00:00:00',208,'Self-Help',15.99,9.59,7.67,'.png','2018-01-01 00:00:00',0,'Falling in love is easy. Staying in loveâ€�?thatâ€™s the challenge. How can you keep your relationship fresh and growing amid the demands, conflicts, and just plain boredom of everyday life?');
INSERT INTO book VALUES ('978-0762447695','You Are a Badass: How to Stop Doubting Your Greatness and Start Living an Awesome Life',13,'2013-04-23 00:00:00',256,'Self-Help',16.00,9.59,7.67,'.png','2018-01-01 00:00:00',0,"In this refreshingly entertaining how-to guide, bestselling author and world-traveling success coach, Jen Sincero, serves up 27 bitesized chapters full of hilariously inspiring stories, sage advice, easy exercises, and the occasional swear word, helping you to: Identify and change the self-sabotaging beliefs and behaviors that stop you from getting what you want, Create a life you totally love. And create it NOW, Make some damn money already. The kind you've never made before.");
INSERT INTO book VALUES ('978-1501175565','What Happened',14,'2017-09-12 00:00:00',512,'Politics',30.00,17.33,13.86,'.png','2018-01-01 00:00:00',0,'For the first time, Hillary Rodham Clinton reveals what she was thinking and feeling during one of the most controversial and unpredictable presidential elections in history. Now free from the constraints of running, Hillary takes you inside the intense personal experience of becoming the first woman nominated for president by a major party in an election marked by rage, sexism, exhilarating highs and infuriating lows, stranger-than-fiction twists, Russian interference, and an opponent who broke all the rules. This is her most personal memoir yet.');
INSERT INTO book VALUES ('978-0679805274',"Oh, the Places You'll Go!",15,'1990-01-22 00:00:00',56,'Children',18.99,15.42,12.34,'.png','2018-01-01 00:00:00',0,'From soaring to high heights and seeing great sights to being left in a Lurch on a prickle-ly perch, Dr. Seuss addresses lifeâ€™s ups and downs with his trademark humorous verse and illustrations, while encouraging readers to find the success that lies within. In a starred review, Booklist notes, â€œSeussâ€™s message is simple but never sappy: life may be a â€˜Great Balancing Act,â€™ but through it all â€˜Thereâ€™s fun to be done.â€™â€� A perennial favorite and a perfect gift for anyone starting a new phase in their life!');
INSERT INTO book VALUES ('978-1455570249','Make Your Bed: Little Things That Can Change Your Life...And Maybe the World',16,'2017-04-04 00:00:00',144,'Self Help',18.00,10.40,5.63,'.png','2018-01-01 00:00:00',0,"On May 17, 2014, Admiral William H. McRaven addressed the graduating class of the University of Texas at Austin on their Commencement day. Taking inspiration from the university's slogan, 'What starts here changes the world,' he shared the ten principles he learned during Navy Seal training that helped him overcome challenges not only in his training and long Naval career, but also throughout his life; and he explained how anyone can use these basic lessons to change themselves-and the world-for the better.");
INSERT INTO book VALUES ('978-0451524935','1984 (Signet Classics)',17,'1970-01-01 11:11:11',328,'Fiction',9.99,6.18,4.94,'.png','2018-01-01 00:00:00',0,'Winston Smith toes the Party line, rewriting history to satisfy the demands of the Ministry of Truth. With each lie he writes, Winston grows to hate the Party that seeks power for its own sake and persecutes those who dare to commit thoughtcrimes. But as he starts to think for himself, Winston canâ€™t escape the fact that Big Brother is always watching...');
INSERT INTO book VALUES ('978-0671027032','How to Win Friends & Influence People',18,'1998-10-01 00:00:00',288,'Self-Help',16.00,9.60,7.68,'.png','2018-01-01 00:00:00',0,'Dale Carnegieâ€™s rock-solid, time-tested advice has carried countless people up the ladder of success in their business and personal lives. One of the most groundbreaking and timeless bestsellers of all time, How to Win Friends & Influence People will teach you:');
INSERT INTO book VALUES ('978-0385514231','Origin: A Novel',19,'2017-10-03 00:00:00',480,'Fiction',29.95,17.77,14.22,'.png','2018-01-01 00:00:00',0,'Robert Langdon, Harvard professor of symbology and religious iconology, arrives at the ultramodern Guggenheim Museum Bilbao to attend a major announcementâ€�?the unveiling of a discovery that â€œwill change the face of science forever.â€� The eveningâ€™s host is Edmond Kirsch, a forty-year-old billionaire and futurist whose dazzling high-tech inventions and audacious predictions have made him a renowned global figure. Kirsch, who was one of Langdonâ€™s first students at Harvard two decades earlier, is about to reveal an astonishing breakthrough . . . one that will answer two of the fundamental questions of human existence.');
INSERT INTO book VALUES ('978-0545935203','Dog Man Unleashed',20,'2016-12-27 00:00:00',224,'Children',9.99,8.48,6.78,'.png','2018-01-01 00:00:00',0,'Dog Man, the newest hero from the creator of Captain Underpants, is still learning a few tricks of the trade. Petey the cat is out of the bag, and his criminal curiosity is taking the city by storm. Something fishy is going on! Can Dog Man unleash justice on this ruffian in time to save the city, or will Petey get away with the purr-fect crime?');
INSERT INTO book VALUES ('978-1101883082','Lilac Girls: A Novel',21,'2017-02-28 00:00:00',512,'Fiction',17.00,12.75,10.20,'.png','2018-01-01 00:00:00',0,'The lives of these three women are set on a collision course when the unthinkable happens and Kasia is sent to RavensbrÃ¼ck, the notorious Nazi concentration camp for women. Their stories cross continentsâ€�?from New York to Paris, Germany, and Polandâ€�?as Caroline and Kasia strive to bring justice to those whom history has forgotten.');
INSERT INTO book VALUES ('978-1449486792','The Sun and Her Flowers',1,'2017-10-03 00:00:00',256,'Fiction',16.99,9.90,7.92,'.png','2018-01-01 00:00:00',0,'From Rupi Kaur, the #1 New York Times bestselling author of milk and honey, comes her long-awaited second collection of poetry.  A vibrant and transcendent journey about growth and healing. Ancestry and honoring oneâ€™s roots. Expatriation and rising up to find a home within yourself.');
INSERT INTO book VALUES ('978-1416947370','Dear Zoo: A Lift-the-Flap Book',22,'2007-05-08 00:00:00',18,'Children',6.99,4.71,3.77,'.png','2018-01-01 00:00:00',0,"This classic children's storybook about a youngster loooking for a perfect pet is sure to delight a new generation of readers!");
INSERT INTO book VALUES ('978-1878424310','The Four Agreements: A Practical Guide to Personal Freedom (A Toltec Wisdom Book)',23,'1997-11-07 00:00:00',160,'Religion & Spirituality',12.95,7.79,6.23,'.png','2018-01-01 00:00:00',0,'In The Four Agreements, bestselling author don Miguel Ruiz reveals the source of self-limiting beliefs that rob us of joy and create needless suffering.');
INSERT INTO book VALUES ('978-0671449025','The Going-To-Bed Book',22,'1982-11-30 00:00:00',14,'Children',5.99,4.19,3.35,'.png','2018-01-01 00:00:00',0,'This classic bedtime story is just right for winding down the day as a joyful, silly group of animals scrub scrub scrub in the tub, brush and brush and brush their teeth, and finally rock and rock and rock to sleep.');
INSERT INTO book VALUES ('978-1595620156','StrengthsFinder 2.0',24,'2007-02-01 00:00:00',175,'Business',31.99,17.79,14.23,'.png','2018-01-01 00:00:00',0,'Loaded with hundreds of strategies for applying your strengths, this new book and accompanying website will change the way you look at yourself--and the world around you--forever. ');
INSERT INTO book VALUES ('978-0385376716','The Wonderful Things You Will Be',25,'2015-08-25 00:00:00',36,'Children',17.99,12.36,9.89,'.png','2018-01-01 00:00:00',0,"From brave and bold to creative and clever, Emily Winfield Martin's rhythmic rhyme expresses all the loving things that parents think of when they look at their children. With beautiful, and sometimes humorous, illustrations, and a clever gatefold with kids in costumes, this is a book grown-ups will love reading over and over to kidsâ€�?both young and old. A great gift for any occasion, but a special stand-out for baby showers, birthdays, and graduation. The Wonderful Things You Will Be has a loving and truthful message that will endure for lifetimes.");
INSERT INTO book VALUES ('978-0964729230','The Shack',26,'2007-07-01 00:00:00',256,'Religion & Spirituality',15.00,8.81,7.05,'.png','2018-01-01 00:00:00',0,"Mackenzie Allen Philips' youngest daughter, Missy, has been abducted during a family vacation and evidence that she may have been brutally murdered is found in an abandoned shack deep in the Oregon wilderness. Four years later in the midst of his Great Sadness, Mack receives a suspicious note, apparently from God, inviting him back to that shack for a weekend. Against his better judgment he arrives at the shack on a wintry afternoon and walks back into his darkest nightmare. What he finds there will change Mack's world forever.");
INSERT INTO book VALUES ('978-1607747307','The Life-Changing Magic of Tidying Up: The Japanese Art of Decluttering and Organizing',27,'2014-10-14 00:00:00',224,'Politics & Social Science',16.99,9.89,7.91,'.png','2018-01-01 00:00:00',0,"The Life-Changing Magic of Tidying Up: The Japanese Art of Decluttering and Organizing Japanese organizational consultant Marie Kondo takes tidying to a whole new level, promising that if you properly declutter your home once, you'll never have to do it again. Whereas most methods advocate a room-by-room or little-by-little approach, the KonMari Method's category-by-category, all-at-once prescription leads to lasting results. In fact, none of Kondo's clients have been repeat customers (and she still has a three-month waiting list of new customers!). With detailed guidance for every type of item in the household, this quirky little manual from Japan's newest lifestyle phenomenon will help readers clear their clutter and enjoy the unique magic of a tidy home--and the calm, motivated mindset it can inspire.");
INSERT INTO book VALUES ('978-1524732684','Option B: Facing Adversity, Building Resilience, and Finding Joy',28,'2017-04-24 00:00:00',240,'Self-Help',25.95,18.16,14.53,'.png','2018-01-01 00:00:00',0,'Resilience comes from deep within us and from support outside us. Even after the most devastating events it is possible to grow by finding deeper meaning and gaining greater appreciation in our lives. Option B illuminates how to help others in crisis. ');
INSERT INTO book VALUES ('978-1623363581','Thug Kitchen',29,'2014-10-07 00:00:00',240,'Cookbook',25.99,15.59,12.47,'.png','2018-01-01 00:00:00',0,'This book is an invitation toeveryone who wants to do better to elevate their kitchen game. No moreketchup and pizza counting as vegetables. No more drive-thru lines. Nomore avoiding the produce corner of the supermarket. Sh*t is about toget real.');
INSERT INTO book VALUES ('978-0316512589','Obama: An Intimate Portrait',30,'2017-11-07 00:00:00',352,'Biography',50.00,31.45,25.16,'.png','2018-01-01 00:00:00',0,"Relive the extraordinary Presidency of Barack Obama through White House photographer Pete Souza's behind-the-scenes images and stories in this #1 New York Timesbestseller--with a foreword from the President himself.");
INSERT INTO book VALUES ('978-1433805615','Publication Manual of the American Psychological Association, 6th Edition',31,'2009-07-15 00:00:00',272,'Reference',29.95,23.96,19.17,'.png','2018-01-01 00:00:00',0,"The Publication Manual is the style manual of choice for writers, editors, students, and educators. Although it is specifically designed to help writers in the behavioral sciences and social sciences, anyone who writes non-fiction prose can benefit from its guidance. The newly-revised Sixth Edition has not only been rewritten. It has also been thoroughly rethought and reorganized, making it the most user-friendly Publication Manual the APA has ever produced. You will be able to find answers to your questions faster than ever before. When you need advice on how to present information, including text, data, and graphics, for publication in any type of format--such as college and university papers, professional journals, presentations for colleagues, and online publication--you will find the advice you're looking for in the Publication Manual.");
INSERT INTO book VALUES ('978-0670026197','A Gentleman in Moscow: A Novel',32,'2016-09-06 00:00:00',480,'Fiction',27.00,18.90,15.12,'.png','2018-01-01 00:00:00',0,'Brimming with humor, a glittering cast of characters, and one beautifully rendered scene after another, this singular novel casts a spell as it relates the countâ€™s endeavor to gain a deeper understanding of what it means to be a man of purpose.');
INSERT INTO book VALUES ('978-0694003617','Goodnight Moon',33,'2007-01-23 00:00:00',30,'Children',8.99,6.19,4.95,'.png','2018-01-01 00:00:00',0,'In a great green room, tucked away in bed, is a little bunny. "Goodnight room, goodnight moon." And to all the familiar things in the softly lit roomâ€�?to the picture of the three little bears sitting on chairs, to the clocks and his socks, to the mittens and the kittens, to everything one by oneâ€�?the little bunny says goodnight.');
INSERT INTO book VALUES ('978-1595141712','Thirteen Reasons Why',34,'2007-10-18 00:00:00',288,'Teens',18.99,11.98,9.58,'.png','2018-01-01 00:00:00',0,"Clay Jensen returns home from school to find a strange package with his name on it lying on his porch. Inside he discovers several cassette tapes recorded by Hannah Bakerâ€�?his classmate and crushâ€�?who committed suicide two weeks earlier. Hannah's voice tells him that there are thirteen reasons why she decided to end her life. Clay is one of them. If he listens, he'll find out why.");
INSERT INTO book VALUES ('978-1501139154','Leonardo da Vinci',14,'2017-10-17 00:00:00',624,'Biography',35.00,20.98,16.78,'.png','2018-01-01 00:00:00',0,'Leonardoâ€™s delight at combining diverse passions remains the ultimate recipe for creativity. So, too, does his ease at being a bit of a misfit: illegitimate, gay, vegetarian, left-handed, easily distracted, and at times heretical. His life should remind us of the importance of instilling, both in ourselves and our children, not just received knowledge but a willingness to question itâ€�?to be imaginative and, like talented misfits and rebels in any era, to think different.');
INSERT INTO book VALUES ('978-0374360979','The Pout-Pout Fish',35,'2013-08-06 00:00:00',34,'Children',7.99,5.79,4.63,'.png','2018-01-01 00:00:00',0,"Swim along with the pout-pout fish as he discovers that being glum and spreading dreary wearies isn't really his destiny. Bright ocean colors and playful rhyme come together in Deborah Diesen's fun fish story that's sure to turn even the poutiest of frowns upside down.");
INSERT INTO book VALUES ('978-0545791342','Harry Potter and the Prisoner of Azkaban: The Illustrated Edition',45,'2017-10-03 00:00:00',336,'Children',39.99,31.99,25.59,'.png','2018-01-01 00:00:00',0,"Harry Potter isn't safe not even within the walls of his magical school. Surrounded by his friends because on top of it all there may well be a traitor in their midst.");
INSERT INTO book VALUES ('978-0374300210','If Animals Kissed Goodnight',34,'2014-06-03 00:00:00',34,'Children',9.99,7.19,5.75,'.png','2018-01-01 00:00:00',0,'With whimsical art and playful rhyming verse, this picture book is now in board book format for the first time, perfect for bedtime snuggles.');
INSERT INTO book VALUES ('978-0805047905','Brown Bear, Brown Bear, What Do You See?',36,'1996-09-15 00:00:00',28,'Children',9.43,6.08,4.86,'.png','2018-01-01 00:00:00',0,"A big happy frog, a plump purple cat, a handsome blue horse, and a soft yellow duck--all parade across the pages of this delightful book. Children will immediately respond to Eric Carle's flat, boldly colored collages. Combined with Bill Martin's singsong text, they create unforgettable images of these endearing animals");
INSERT INTO book VALUES ('978-0786965601',"Player's Handbook (Dungeons & Dragons)",37,'2014-08-19 00:00:00',320,'Puzzles & Games',49.95,33.19,26.55,'.png','2018-01-01 00:00:00',0,'Dungeons & Dragons immerses you in a world of adventure. Explore ancient ruins and deadly dungeons. Battle monsters while searching for legendary treasures. Gain experience and power as you trek across uncharted lands with your companions.');
INSERT INTO book VALUES ('978-1419708459','Rosie Revere, Engineer',5,'2013-09-03 00:00:00',32,'Children',17.95,12.08,9.66,'.png','2018-01-01 00:00:00',0,"Rosie may seem quiet during the day, but at night she's a brilliant inventor of gizmos and gadgets who dreams of becoming a great engineer. When her great-great-aunt Rose (Rosie the Riveter) comes for a visit and mentions her one unfinished goal--to fly--Rosie sets to work building a contraption to make her aunt's dream come true. But when her contraption doesn't fl y but rather hovers for a moment and then crashes, Rosie deems the invention a failure. On the contrary, Aunt Rose inisists that Rosie's contraption was a raging success. You can only truly fail, she explains, if you quit.");
INSERT INTO book VALUES ('978-1627790642','Killing England: The Brutal Struggle for American Independence',36,'2017-09-19 00:00:00',352,'History',30.00,15.00,12.00,'.png','2018-01-01 00:00:00',0,"The breathtaking latest installment in Bill Oâ€™Reilly and Martin Dugardâ€™s mega-bestselling Killing series transports readers to the most important era in our nationâ€™s history, the Revolutionary War. Told through the eyes of George Washington, Benjamin Franklin, Thomas Jefferson, and Great Britainâ€™s King George III, Killing England chronicles the path to independence in gripping detail, taking the reader from the battlefields of America to the royal courts of Europe. What started as protest and unrest in the colonies soon escalated to a world war with devastating casualties. Oâ€™Reilly and Dugard recreate the warâ€™s landmark battles, including Bunker Hill, Long Island, Saratoga, and Yorktown, revealing the savagery of hand-to-hand combat and the often brutal conditions under which these brave American soldiers lived and fought. Also here is the reckless treachery of Benedict Arnold and the daring guerilla tactics of the 'Swamp Fox' Frances Marion. A must read, Killing England reminds one and all how the course of history can be changed through the courage and determination of those intent on doing the impossible.");
INSERT INTO book VALUES ('978-0803736801','Dragons Love Tacos',38,'2012-06-14 00:00:00',40,'Children',16.99,11.45,9.16,'.png','2018-01-01 00:00:00',0,"Dragons love tacos. They love chicken tacos, beef tacos, great big tacos, and teeny tiny tacos. So if you want to lure a bunch of dragons to your party, you should definitely serve tacos. Buckets and buckets of tacos. Unfortunately, where there are tacos, there is also salsa. And if a dragon accidentally eats spicy salsa . . . oh, boy. You're in red-hot trouble.");
INSERT INTO book VALUES ('978-1595555274','The Total Money Makeover: Classic Edition: A Proven Plan for Financial Fitness',39,'2017-09-17 00:00:00',272,'Personal Finance',24.99,14.49,11.59,'.png','2018-01-01 00:00:00',0,'By now, youâ€™ve heard all the nutty get-rich-quick schemes, the fiscal diet fads that leave you with a lot of kooky ideas but not a penny in your pocket. Hey, if youâ€™re tired of the lies and sick of the false promises, take a look at thisâ€�?itâ€™s the simplest, most straightforward game plan for completely making over your money habits. And itâ€™s based on results, not pie-in-the-sky fantasies.');
INSERT INTO book VALUES ('978-1501100109','10-Day Green Smoothie Cleanse',40,'2014-07-15 00:00:00',192,'Health',15.99,9.59,7.67,'.png','2018-01-01 00:00:00',0,'This book provides a shopping list, recipes, and detailed instructions for the 10-day cleanse, along with suggestions for getting the best results. It also offers advice on how to continue to lose weight and maintain good health afterwards. Are you ready to look slimmer, healthier, and sexier than you have in years? Then get ready to begin the 10-Day Green Smoothie Cleanse!');
INSERT INTO book VALUES ('978-0545795661','PokÃ©mon Deluxe Essential Handbook: The Need-to-Know Stats and Facts on Over 700 PokÃ©mon',41,'2015-07-28 00:00:00',432,'Children',11.99,8.39,6.71,'.png','2018-01-01 00:00:00',0,"This comprehensive guide book is an absolute must-have for PokÃ©mon Trainers of all ages! It's got all the facts and figures you ever wanted to know about PokÃ©mon in one convenient, easy-to-read format. And it's the perfect reference for Trainers looking to master the world of  PokÃ©mon Go.");
INSERT INTO book VALUES ('978-1623158088','The Complete Ketogenic Diet for Beginners: Your Essential Guide to Living the Keto Lifestyle',6,'2016-12-20 00:00:00',158,'Health',11.99,8.50,6.80,'.png','2018-01-01 00:00:00',0,'Endorsed by the Mayo Clinic and others in the medical community, the ketogenic diet has been proven as a healthy, effective way of achieving weight loss, as it consists of low-carb, high fat foods that prompt the body to burn fat for energy instead of glucose.');
INSERT INTO book VALUES ('978-1592408412','Daring Greatly: How the Courage to Be Vulnerable Transforms the Way We Live, Love, Parent, and Lead',42,'2015-04-07 00:00:00',320,'Self-Help',17.00,13.59,10.87,'.png','2018-01-01 00:00:00',0,'Daring Greatly is not about winning or losing. Itâ€™s about courage. In a world where â€œnever enoughâ€� dominates and feeling afraid has become second nature, vulnerability is subversive. Uncomfortable. Itâ€™s even a little dangerous at times. And, without question, putting ourselves out there means thereâ€™s a far greater risk of getting criticized or feeling hurt. But when we step back and examine our lives, we will find that nothing is as uncomfortable, dangerous, and hurtful as standing on the outside of our lives looking in and wondering what it would be like if we had the courage to step into the arenaâ€�?whether itâ€™s a new relationship, an important meeting, the creative process, or a difficult family conversation. Daring Greatly is a practice and a powerful new vision for letting ourselves be seen.');
INSERT INTO book VALUES ('978-1501124020','Principles: Life and Work',14,'2017-09-19 00:00:00',592,'Business',30.00,17.99,14.39,'.png','2018-01-01 00:00:00',0,'In Principles, Dalio shares what heâ€™s learned over the course of his remarkable career. He argues that life, management, economics, and investing can all be systemized into rules and understood like machines. The bookâ€™s hundreds of practical lessons, which are built around his cornerstones of â€œradical truthâ€� and â€œradical transparency,â€� include Dalio laying out the most effective ways for individuals and organizations to make decisions, approach challenges, and build strong teams. He also describes the innovative tools the firm uses to bring an idea meritocracy to life, such as creating â€œbaseball cardsâ€� for all employees that distill their strengths and weaknesses, and employing computerized decision-making systems to make believability-weighted decisions. While the book brims with novel ideas for organizations and institutions, Principles also offers a clear, straightforward approach to decision-making that Dalio believes anyone can apply, no matter what theyâ€™re seeking to achieve.');
INSERT INTO book VALUES ('978-1455540419','Al Franken, Giant of the Senate',43,'2017-05-30 00:00:00',416,'Politics & Social Science',28.00,14.99,11.99,'.png','2018-01-01 00:00:00',0,'In this candid personal memoir, the honorable gentleman from Minnesota takes his army of loyal fans along with him from Saturday Night Live to the campaign trail, inside the halls of Congress, and behind the scenes of some of the most dramatic and/or hilarious moments of his new career in politics. Has Al Franken become a true Giant of the Senate? Franken asks readers to decide for themselves.');
INSERT INTO book VALUES ('978-1423160939','Magnus Chase and the Gods of Asgard, Book 3 The Ship of the Dead',44,'2017-10-03 00:00:00',432,'Children',19.99,14.23,11.38,'.png','2018-01-01 00:00:00',0,"Magnus Chase, a once-homeless teen, is a resident of the Hotel Valhalla and one of Odin's chosen warriors. As the son of Frey, the god of summer, fertility, and health, Magnus isn't naturally inclined to fighting. But he has strong and steadfast friends, including Hearthstone the elf, Blitzen the dwarf, and Samirah the Valkyrie, and together they have achieved brave deeds, such as defeating Fenris Wolf and battling giants for Thor's hammer, Mjolnir. Now Magnus faces his most dangerous trial yet. His cousin, Annabeth, recruits her boyfriend, Percy Jackson, to give Magnus some pointers, but will his training be enough? Loki is free from his chains. He's readying Naglfar, the Ship of the Dead, complete with a host of giants and zombies, to sail against the Asgardian gods and begin the final battle of Ragnarok. It's up to Magnus and his friends to stop him, but to do so they will have to sail across the oceans of Midgard, Jotunheim, and Niflheim in a desperate race to reach Naglfar before it's ready to sail. Along the way, they will face angry sea gods, hostile giants, and an evil fire-breathing dragon. Magnus's biggest challenge will be facing his own inner demons. Does he have what it takes to outwit the wily trickster god?");
INSERT INTO book VALUES ('978-0545790352',"Harry Potter and the Sorcerer's Stone: The Illustrated Edition",45,'2015-10-06 00:00:00',256,'Children',39.99,27.99,22.39,'.png','2018-01-01 00:00:00',0,"Harry Potter has never been the star of a Quidditch team, scoring points while riding a broom far above the ground. He knows no spells, has never helped to hatch a dragon, and has never worn a cloak of invisibility. All he knows is a miserable life with the Dursleys, his horrible aunt and uncle, and their abominable son, Dudley -- a great big swollen spoiled bully. Harry's room is a tiny closet at the foot of the stairs, and he hasn't had a birthday party in eleven years. But all that is about to change when a mysterious letter arrives by owl messenger: a letter with an invitation to an incredible place that Harry -- and anyone who reads about him -- will find unforgettable.");
INSERT INTO book VALUES ('978-0399226908','The Very Hungry Caterpillar',46,'1994-03-23 00:00:00',26,'Children',9.99,6.89,5.51,'.png','2018-01-01 00:00:00',0,'THE all-time classic picture book, from generation to generation, sold somewhere in the world every 30 seconds! Have you shared it with a child or grandchild in your life?');
INSERT INTO book VALUES ('978-0553522778','Puppy Birthday to You!',47,'2015-07-28 00:00:00',24,'Children',6.99,4.96,3.97,'.png','2018-01-01 00:00:00',0,'Can the puppies from Nickelodeonâ€™s PAW Patrol throw Chase a surprise birthday party AND save Adventure Bay? This action-packed Little Golden Book is sure to thrill boys and girls ages 2 to 5.');
INSERT INTO book VALUES ('978-0800788032','Laugh-Out-Loud Jokes for Kids',48,'2010-08-01 00:00:00',128,'Children',4.99,3.61,2.89,'.png','2018-01-01 00:00:00',0,'What happens to race car drivers when they eat too much? They get indy-gestion. Laugh-Out-Loud Jokes for Kids provides children ages 7-10 many hours of fun and laughter. Young readers will have a blast sharing this collection of hundreds of one-liners, knock knock jokes, tongue twisters, and more with their friends and family! This mega-bestselling book will have children rolling on the floor with laughter and is sure to be a great gift idea for any child.');
INSERT INTO book VALUES ('978-1328683786','Tools of Titans: The Tactics, Routines, and Habits of Billionaires, Icons, and World-Class Performers',10,'2016-12-06 00:00:00',736,'Self-Help',30.00,18.00,14.40,'.png','2018-01-01 00:00:00',0,'â€œEverything within these pages has been vetted, explored, and applied to my own life in some fashion. Iâ€™ve used dozens of the tactics and philosophies in high-stakes negotiations, high-risk environments, or large business dealings. The lessons have made me millions of dollars and saved me years of wasted effort and frustration.');
INSERT INTO book VALUES ('978-1973124269','Milk and Vine',49,'2017-10-22 00:00:00',77,'Humor',7.99,5.99,4.79,'.png','2018-01-01 00:00:00',0,'Parodying the popular poetry book Milk and Honey, Milk and Vine offers a beautifully designed reflection of the thought-provoking ideas that spread through this amazing platform.');
INSERT INTO book VALUES ('978-1484746424','The Trials of Apollo Book Two The Dark Prophecy',44,'2017-05-02 00:00:00',432,'Fiction',19.99,12.09,9.67,'.png','2018-01-01 00:00:00',0,'Zeus has punished his son Apollo--god of the sun, music, archery, poetry, and more--by casting him down to earth in the form of a gawky, acne-covered sixteen-year-old mortal named Lester. The only way Apollo can reclaim his rightful place on Mount Olympus is by restoring several Oracles that have gone dark. What is affecting the Oracles, and how can Apollo/Lester do anything about them without his powers?');
INSERT INTO book VALUES ('978-0553496673','Everything, Everything',50,'2017-03-07 00:00:00',352,'Teens',10.99,8.79,7.03,'.png','2018-01-01 00:00:00',0,'What if you couldnâ€™t touch anything in the outside world? Never breathe in the fresh air, feel the sun warm your face . . . or kiss the boy next door? In Everything, Everything, Maddy is a girl whoâ€™s literally allergic to the outside world, and Olly is the boy who moves in next door . . . and becomes the greatest risk sheâ€™s ever taken.');
INSERT INTO book VALUES ('978-1591846444','Start with Why: How Great Leaders Inspire Everyone to Take Action',51,'2011-12-27 00:00:00',256,'Business',16.00,12.85,10.28,'.png','2018-01-01 00:00:00',0,'In 2009, Simon Sinek started a movement to help people become more inspired at work, and in turn inspire their colleagues and customers. Since then, millions have been touched by the power of his ideas, including more than 28 million whoâ€™ve watched his TED Talk based on START WITH WHY -- the third most popular TED video of all time.');
INSERT INTO book VALUES ('978-0393609097','Norse Mythology',8,'2017-02-07 00:00:00',304,'Fiction',25.95,18.16,14.53,'.png','2018-01-01 00:00:00',0,'In Norse Mythology, Gaiman stays true to the myths in envisioning the major Norse pantheon: Odin, the highest of the high, wise, daring, and cunning; Thor, Odinâ€™s son, incredibly strong yet not the wisest of gods; and Lokiâ€•son of a giantâ€•blood brother to Odin and a trickster and unsurpassable manipulator.');
INSERT INTO book VALUES ('978-1503943377','Beneath a Scarlet Sky: A Novel',52,'2017-05-01 00:00:00',524,'Fiction',14.95,8.63,6.90,'.png','2018-01-01 00:00:00',0,'Pino Lella wants nothing to do with the war or the Nazis. Heâ€™s a normal Italian teenagerâ€�?obsessed with music, food, and girlsâ€�?but his days of innocence are numbered. When his family home in Milan is destroyed by Allied bombs, Pino joins an underground railroad helping Jews escape over the Alps, and falls for Anna, a beautiful widow six years his senior.');
INSERT INTO book VALUES ('978-1400205875','Uninvited: Living Loved When You Feel Less Than, Left Out, and Lonely',39,'2016-08-09 00:00:00',288,'Religion',16.99,10.71,8.57,'.png','2018-01-01 00:00:00',0,'The enemy wants us to feel rejected . . . left out, lonely, and less than.');
INSERT INTO book VALUES ('978-0544854413','The Whole30 Cookbook: 150 Delicious and Totally Compliant Recipes to Help You Succeed with the Whole30 and Beyond',10,'2016-12-06 00:00:00',320,'Nutrition',30.00,20.98,16.78,'.png','2018-01-01 00:00:00',0,'The groundbreaking Whole30 program has helped countless people transform their lives by bringing them better sleep, more energy, fewer cravings, weight loss, and new healthy habits that last a lifetime. In this cookbook, best-selling author and Whole30 co-creator Melissa Hartwig delivers over 150 all-new recipes to help readers prepare delicious, healthy meals during their Whole30 and beyond.');
INSERT INTO book VALUES ('978-1609580834','The Care and Keeping of You: The Body Book for Younger Girls, Revised Edition',53,'2012-03-26 00:00:00',104,'Children',12.99,8.89,7.11,'.png','2018-01-01 00:00:00',0,"Your best-selling body book for girls just got even better! With all-new illustrations and updated content for girls ages 8 and up, it features tips, how-tos, and facts from the experts. (Medical consultant: Cara Natterson, MD.) You'll find answers to questions about your changing body, from hair care to healthy eating, bad breath to bras, periods to pimples, and everything in between. Once you feel comfortable with what's happening, you'll be ready to move on to the The Care & Keeping of You 2!");
INSERT INTO book VALUES ('978-0743247542','The Glass Castle: A Memoir',54,'2006-01-17 00:00:00',288,'Biography',17.00,9.69,7.75,'.png','2018-01-01 00:00:00',0,'The Glass Castle is a remarkable memoir of resilience and redemption, and a revelatory look into a family at once deeply dysfunctional and uniquely vibrant. When sober, Jeannetteâ€™s brilliant and charismatic father captured his childrenâ€™s imagination, teaching them physics, geology, and how to embrace life fearlessly. But when he drank, he was dishonest and destructive. Her mother was a free spirit who abhorred the idea of domesticity and didnâ€™t want the responsibility of raising a family.');
INSERT INTO book VALUES ('978-0743273565','THE GREAT GATSBY',54,'2004-09-30 00:00:00',180,' Literature & Fiction',16.00,9.19,7.35,'.png','2018-01-01 00:00:00',0,"The Great Gatsby, F. Scott Fitzgerald's third book, stands as the supreme achievement of his career. This exemplary novel of the Jazz Age has been acclaimed by generations of readers. The story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan, of lavish parties on Long Island at a time when The New York Times noted 'gin was the national drink and sex the national obsession,' it is an exquisitely crafted tale of America in the 1920s.");
INSERT INTO book VALUES ('978-0374533557','Thinking, Fast and Slow',35,'2013-04-02 00:00:00',499,'Medical Applied Psychology',17.00,9.78,7.82,'.png','2018-01-01 00:00:00',0,'In the international bestseller, Thinking, Fast and Slow, Daniel Kahneman, the renowned psychologist and winner of the Nobel Prize in Economics, takes us on a groundbreaking tour of the mind and explains the two systems that drive the way we think. System 1 is fast, intuitive, and emotional; System 2 is slower, more deliberative, and more logical. The impact of overconfidence on corporate strategies, the difficulties of predicting what will make us happy in the future, the profound effect of cognitive biases on everything from playing the stock market to planning our next vacationâ€•each of these can be understood only by knowing how the two systems shape our judgments and decisions.');
INSERT INTO book VALUES ('978-0307887443','Ready Player One: A Novel',55,'2012-06-05 00:00:00',400,'Science Fiction',16.00,9.19,7.35,'.png','2018-01-01 00:00:00',0,"In the year 2045, reality is an ugly place. The only time teenage Wade Watts really feels alive is when he's jacked into the virtual utopia known as the OASIS. Wade's devoted his life to studying the puzzles hidden within this world's digital confinesâ€�?puzzles that are based on their creator's obsession with the pop culture of decades past and that promise massive power and fortune to whoever can unlock them. But when Wade stumbles upon the first clue, he finds himself beset by players willing to kill to take this ultimate prize. The race is on, and if Wade's going to survive, he'll have to winâ€�?and confront the real world he's always been so desperate to escape.");
INSERT INTO book VALUES ('978-0545935210','Dog Man: A Tale of Two Kitties',20,'2017-08-29 00:00:00',260,'Children',9.99,6.08,4.86,'.png','2018-01-01 00:00:00',0,"He was the best of dogs... He was the worst of dogs... It was the age of invention... It was the season of surprise... It was the eve of supa sadness... It was the dawn of hope... Dog Man, the newest hero from the creator of Captain Underpants, hasn't always been a paws-itive addition to the police force. While he can muzzle miscreants, he tends to leave a slick of slobber in his wake! This time, Petey the cat's dragged in a tiny bit of trouble -- a double in the form of a super-cute kitten. Dog Man will have to work twice as hard to bust these furballs and remain top dog!");
INSERT INTO book VALUES ('978-1451639612','The 7 Habits of Highly Effective People: Powerful Lessons in Personal Change',14,'2013-11-19 00:00:00',432,'Self-Help',17.00,11.47,9.18,'.png','2018-01-01 00:00:00',0,'One of the most inspiring and impactful books ever written, The 7 Habits of Highly Effective People has captivated readers for 25 years. It has transformed the lives of Presidents and CEOs, educators and parentsâ€�? in short, millions of people of all ages and occupations.');
INSERT INTO book VALUES ('978-1250138064','The Wisdom of Sundays: Life-Changing Insights from Super Soul Conversations',56,'2017-10-17 00:00:00',240,'Mental & Spiritual Healing',27.99,16.79,13.43,'.png','2018-01-01 00:00:00',0,'Organized into ten chaptersâ€•each one representing a powerful step in Oprahâ€™s own spiritual journey and introduced with an intimate, personal essay by Oprah herselfâ€•the Wisdom of Sundays features selections from the most meaningful conversations between Oprah and some of todayâ€™s most-admired thought-leaders. Visionaries like Tony Robbins, Arianna Huffington, and Shonda Rhimes share their lessons in finding purpose through mindfulness and intention. World renowned authors and teachers like Eckhart Tolle, Thich Nhat Hahn, Marianne Williamson and Wayne Dyer, explain our complex relationship with the ego and the healing powers of love and connection; and award-winning and bestselling writers like Cheryl Strayed, Elizabeth Gilbert, and Elizabeth Lesser explore the beauty of forgiveness and spirituality.');
INSERT INTO book VALUES ('978-0385383691','P is for Potty! (Sesame Street) (Lift-the-Flap)',15,'2014-07-22 00:00:00',12,'Children',5.99,5.99,4.79,'.png','2018-01-01 00:00:00',0,"Sesame Street's Elmo tells little girls and boys ages 1 to 3 all about how to use the potty in this sturdy lift-the-flap board book with more than 30 flaps to find and open. It's the perfect mix of fun and learning for potty-training toddlersâ€�?especially while they practice sitting on the potty! Sturdy flaps will hold up to hours of repeat lifting and peeking, and toddlers will delight in the surprises they find under the flaps.");
INSERT INTO book VALUES ('978-0385541176','The Rooster Bar',19,'2017-10-24 00:00:00',368,'Thriller & Suspense',28.85,16.41,13.13,'.png','2018-01-01 00:00:00',0,'Mark, Todd, and Zola came to law school to change the world, to make it a better place. But now, as third-year students, these close friends realize they have been duped. They all borrowed heavily to attend a third-tier, for-profit law school so mediocre that its graduates rarely pass the bar exam, let alone get good jobs. And when they learn that their school is one of a chain owned by a shady New York hedge-fund operator who also happens to own a bank specializing in student loans, the three know they have been caught up in The Great Law School Scam.');
INSERT INTO book VALUES ('978-0060256654','The Giving Tree',57,'1970-01-11 11:11:11',57,'Children',17.99,11.39,9.11,'.png','2018-01-01 00:00:00',0,"Shel Silverstein's incomparable career as a bestselling children's book author and illustrator began with Lafcadio, the Lion Who Shot Back. He is also the creator of picture books including A Giraffe and a Half, Who Wants a Cheap Rhinoceros?, The Missing Piece, The Missing Piece Meets the Big O, and the perennial favorite The Giving Tree, and of classic poetry collections such as Where the Sidewalk Ends, A Light in the Attic, Falling Up, Every Thing On It, Don't Bump the Glump!, and Runny Babbit.");
INSERT INTO book VALUES ('978-1524741723','She Persisted: 13 American Women Who Changed the World',46,'2017-05-30 00:00:00',32,'Children',17.89,12.36,9.89,'.png','2018-01-01 00:00:00',0,"Throughout American history, there have always been women who have spoken out for what's right, even when they have to fight to be heard. In early 2017, Senator Elizabeth Warren's refusal to be silenced in the Senate inspired a spontaneous celebration of women who persevered in the face of adversity. In this book, Chelsea Clinton celebrates thirteen American women who helped shape our country through their tenacity, sometimes through speaking out, sometimes by staying seated, sometimes by captivating an audience. They all certainly persisted.");
INSERT INTO book VALUES ('978-0804190114','On Tyranny: Twenty Lessons from the Twentieth Century',58,'2017-02-28 00:00:00',128,'Politics & Government',8.99,6.39,5.11,'.png','2018-01-01 00:00:00',0,'The Founding Fathers tried to protect us from the threat they knew, the tyranny that overcame ancient democracy. Today, our political order faces new threats, not unlike the totalitarianism of the twentieth century. We are no wiser than the Europeans who saw democracy yield to fascism, Nazism, or communism. Our one advantage is that we might learn from their experience.');
INSERT INTO book VALUES ('978-0062315007','The Alchemist',4,'2014-04-15 00:00:00',208,'Religion & Spirituality',16.99,13.44,10.75,'.png','2018-01-01 00:00:00',0,"Paulo Coelho's masterpiece tells the mystical story of Santiago, an Andalusian shepherd boy who yearns to travel in search of a worldly treasure. His quest will lead him to riches far differentâ€�?and far more satisfyingâ€�?than he ever imagined. Santiago's journey teaches us about the essential wisdom of listening to our hearts, of recognizing opportunity and learning to read the omens strewn along life's path, and, most importantly, to follow our dreams.");
INSERT INTO book VALUES ('978-1451673319','Fahrenheit 451',14,'2012-01-10 00:00:00',249,'Politics & Government',15.99,10.79,8.63,'.png','2018-01-01 00:00:00',0,'Montag never questions the destruction and ruin his actions produce, returning each day to his bland life and wife, Mildred, who spends all day with her television â€œfamily.â€� But then he meets an eccentric young neighbor, Clarisse, who introduces him to a past where people didnâ€™t live in fear and to a present where one sees the world through the ideas in books instead of the mindless chatter of television.');
INSERT INTO book VALUES ('978-0385543026','Camino Island: A Novel',19,'2017-06-06 00:00:00',304,'Thrillers & Suspense',28.95,16.08,12.86,'.png','2018-01-01 00:00:00',0,'A gang of thieves stage a daring heist from a secure vault deep below Princeton Universityâ€™s Firestone Library. Their loot is priceless, but Princeton has insured it for twenty-five million dollars.');
INSERT INTO book VALUES ('978-0525555360','Turtles All the Way Down',59,'2017-10-10 00:00:00',304,'Health, Fitness & Dieting',19.99,11.97,9.58,'.png','2018-01-01 00:00:00',0,'Sixteen-year-old Aza never intended to pursue the mystery of fugitive billionaire Russell Pickett, but thereâ€™s a hundred-thousand-dollar reward at stake and her Best and Most Fearless Friend, Daisy, is eager to investigate. So together, they navigate the short distance and broad divides that separate them from Russell Pickettâ€™s son, Davis.');
INSERT INTO book VALUES ('978-1592858491',"The Gifts of Imperfection: Let Go of Who You Think You're Supposed to Be and Embrace Who You Are",60,'2010-08-27 00:00:00',160,'Self-help',14.95,8.99,7.19,'.png','2018-01-01 00:00:00',0,'n The Gifts of Imperfection, BrenÃ© Brown, a leading expert on shame, authenticity, and belonging, shares ten guideposts on the power of Wholehearted livingâ€�?a way of engaging with the world from a place of worthiness.');
INSERT INTO book VALUES ('978-0920668375','Love You Foreve',61,'1995-09-01 00:00:00',32,'Children',5.95,4.31,3.45,'.png','2018-01-01 00:00:00',0,'So begins the story that has touched the hearts of millions worldwide. Since publication in l986, Love You Forever has sold more than 15 million copies in paperback and the regular hardcover edition (as well as hundreds of thousands of copies in Spanish and French)');
INSERT INTO book VALUES ('978-0062225269','The Pioneer Woman Cooks: Come and Get It!: Simple, Scrumptious Recipes for Crazy Busy Lives',62,'2017-10-24 00:00:00',400,'Cookbooks, Food & Wine',29.99,17.99,14.39,'.png','2018-01-01 00:00:00',0,"Screeeeeech! Reality check! Okay, let's face it: With school, sports, work, obligations, and activities pulling us in a million directions, not many of us can spend that amount of time in the kitchen anymore! What we really need are simple, scrumptious, doable recipes that solve the challenge of serving up hearty, satisfying food (that tastes amazing!) day after day, week afterweek without falling into a rut and relying on the same old rotation of meals. Cooking should be fun, rewarding, and it definitely should feed your soul and feed the people in your household in the process!");
INSERT INTO book VALUES ('978-0345472328','Mindset: The New Psychology of Success',21,'2007-12-26 00:00:00',320,'Education & Teaching',17.00,13.00,10.40,'.png','2018-01-01 00:00:00',0,"After decades of research, world-renowned Stanford University psychologist Carol S. Dweck, Ph.D., discovered a simple but groundbreaking idea: the power of mindset. In this brilliant book, she shows how success in school, work, sports, the arts, and almost every area of human endeavor can be dramatically influenced by how we think about our talents and abilities. People with a fixed mindsetâ€�?those who believe that abilities are fixedâ€�?are less likely to flourish than those with a growth mindsetâ€�?those who believe that abilities can be developed. Mindset reveals how great parents, teachers, managers, and athletes can put this idea to use to foster outstanding accomplishment.");
INSERT INTO book VALUES ('978-0689835605',"Where Is Baby's Belly Button?",22,'2000-09-01 00:00:00',14,'Children',5.99,4.99,3.99,'.png','2018-01-01 00:00:00',0,"Karen Katz's adorable babies play peekaboo in this delightful interactive book. The sturdy format and easy-to-lift flaps are perfect for parents and children to share.");
INSERT INTO book VALUES ('978-1594204876','Grant',63,'2017-10-10 00:00:00',1104,'Civil War',40.00,23.99,19.19,'.png','2018-01-01 00:00:00',0,"Ulysses S. Grant's life has typically been misunderstood. All too often he is caricatured as a chronic loser and an inept businessman, or as the triumphant but brutal Union general of the Civil War. But these stereotypes don't come close to capturing him, as Chernow shows in his masterful biography, the first to provide a complete understanding of the general and president whose fortunes rose and fell with dizzying speed and frequency.");
INSERT INTO book VALUES ('978-0062316097','Sapiens: A Brief History of Humankind',7,'2015-02-10 00:00:00',464,'Science & Math',35.00,21.00,16.80,'.png','2018-01-01 00:00:00',0,'Most books about the history of humanity pursue either a historical or a biological approach, but Dr. Yuval Noah Harari breaks the mold with this highly original book that begins about 70,000 years ago with the appearance of modern cognition. From examining the role evolving humans have played in the global ecosystem to charting the rise of empires, Sapiens integrates history and science to reconsider accepted narratives, connect past developments with contemporary concerns, and examine specific events within the context of larger ideas.');
INSERT INTO book VALUES ('978-0060555665','The Intelligent Investor: The Definitive Book on Value Investing. A Book of Practical Counsel (Revised Edition) (Collins Business Essentials) Paperback â€“',64,'2006-02-21 00:00:00',640,'Business & Money',22.99,14.39,11.51,'.png','2018-01-01 00:00:00',0,"Over the years, market developments have proven the wisdom of Graham's strategies. While preserving the integrity of Graham's original text, this revised edition includes updated commentary by noted financial journalist Jason Zweig, whose perspective incorporates the realities of today's market, draws parallels between Graham's examples and today's financial headlines, and gives readers a more thorough understanding of how to apply Graham's principles.");
INSERT INTO book VALUES ('978-0062427137','The Plant Paradox: The Hidden Dangers in "Healthy" Foods That Cause Disease and Weight Gain',65,'2017-04-25 00:00:00',416,'Health, Fitness & Dieting',27.99,16.72,13.38,'.png','2018-01-01 00:00:00',0,'Most of us have heard of glutenâ€�?a protein found in wheat that causes widespread inflammation in the body. Americans spend billions of dollars on gluten-free diets in an effort to protect their health. But what if weâ€™ve been missing the root of the problem? In The Plant Paradox, renowned cardiologist Dr. Steven Gundry reveals that gluten is just one variety of a common, and highly toxic, plant-based protein called lectin. Lectins are found not only in grains like wheat but also in the â€œgluten-freeâ€� foods most of us commonly regard as healthy, including many fruits, vegetables, nuts, beans, and conventional dairy products. These proteins, which are found in the seeds, grains, skins, rinds, and leaves of plants, are designed by nature to protect them from predators (including humans). Once ingested, they incite a kind of chemical warfare in our bodies, causing inflammatory reactions that can lead to weight gain and serious health conditions.');
INSERT INTO book VALUES ('978-0385534246','Killers of the Flower Moon: The Osage Murders and the Birth of the FBI',19,'2017-04-18 00:00:00',352,'History',28.95,17.37,13.90,'.png','2018-01-01 00:00:00',0,' In Killers of the Flower Moon, David Grann revisits a shocking series of crimes in which dozens of people were murdered in cold blood. Based on years of research and startling new evidence, the book is a masterpiece of narrative nonfiction, as each step in the investigation reveals a series of sinister secrets and reversals. But more than that, it is a searing indictment of the callousness and prejudice toward American Indians that allowed the murderers to operate with impunity for so long. Killers of the Flower Moon is utterly compelling, but also emotionally devastating.');
INSERT INTO book VALUES ('978-1594634024','The Girl on the Train',66,'2016-07-12 00:00:00',336,'Thriller',16.00,9.68,7.74,'.png','2018-01-01 00:00:00',0,'EVERY DAY THE SAME Rachel takes the same commuter train every morning and night. Every day she rattles down the track, flashes past a stretch of cozy suburban homes, and stops at the signal that allows her to daily watch the same couple breakfasting on their deck. Sheâ€™s even started to feel like she knows them. Jess and Jason, she calls them. Their lifeâ€�?as she sees itâ€�?is perfect. Not unlike the life she recently lost.UNTIL TODAY And then she sees something shocking. Itâ€™s only a minute until the train moves on, but itâ€™s enough. Now everythingâ€™s changed. Unable to keep it to herself, Rachel goes to the police. But is she really as unreliable as they say? Soon she is deeply entangled not only in the investigation but in the lives of everyone involved. Has she done more harm than good?');
INSERT INTO book VALUES ('978-1338099133','Harry Potter and the Cursed Child, Parts 1 & 2, Special Rehearsal Edition Script',45,'2016-07-31 00:00:00',320,'Children',29.99,17.99,14.39,'.png','2018-01-01 00:00:00',0,'While Harry grapples with a past that refuses to stay where it belongs, his youngest son Albus must struggle with the weight of a family legacy he never wanted. As past and present fuse ominously, both father and son learn the uncomfortable truth: sometimes, darkness comes from unexpected places."');
INSERT INTO book VALUES ('978-1419723445','Diary of a Wimpy Kid #11: Double Down',5,'2016-11-01 00:00:00',224,'Children',13.95,9.81,7.85,'.png','2018-01-01 00:00:00',0,'The pressureâ€™s really piling up on Greg Heffley. His mom thinks video games are turning his brain to mush, so she wants her son to put down the controller and explore his â€œcreative side.â€� As if thatâ€™s not scary enough, Halloweenâ€™s just around the corner and the frights are coming at Greg from every angle. When Greg discovers a bag of gummy worms, it sparks an idea. Can he get his mom off his back by making a movie . . . and will he become rich and famous in the process? Or will doubling down on this plan just double Gregâ€™s troubles?');
INSERT INTO book VALUES ('978-1627790628','Killing the Rising Sun: How America Vanquished World War II Japan',36,'2016-09-13 00:00:00',336,'History',30.00,16.66,13.33,'.png','2018-01-01 00:00:00',0,'Autumn 1944. World War II is nearly over in Europe but is escalating in the Pacific, where American soldiers face an opponent who will go to any length to avoid defeat. The Japanese army follows the samurai code of Bushido, stipulating that surrender is a form of dishonor. Killing the Rising Sun takes readers to the bloody tropical-island battlefields of Peleliu and Iwo Jima and to the embattled Philippines, where General Douglas MacArthur has made a triumphant return and is plotting a full-scale invasion of Japan. Across the globe in Los Alamos, New Mexico, Dr. J. Robert Oppenheimer and his team of scientists are preparing to test the deadliest weapon known to mankind. In Washington, DC, FDR dies in office and Harry Truman ascends to the presidency, only to face the most important political decision in history: whether to use that weapon. And in Tokyo, Emperor Hirohito, who is considered a deity by his subjects, refuses to surrender, despite a massive and mounting death toll. Told in the same page-turning style of Killing Lincoln, Killing Kennedy, Killing Jesus, Killing Patton, and Killing Reagan, this epic saga details the final moments of World War II like never before.');
INSERT INTO book VALUES ('978-0325401126','Fantastic Beasts and Where to Find Them: The Original Screenplay',45,'2016-11-18 00:00:00',304,'Children',24.99,15.17,12.14,'.png','2018-01-01 00:00:00',0,"When Magizoologist Newt Scamander arrives in New York, he intends his stay to be just a brief stopover. However, when his magical case is misplaced and some of Newt's fantastic beasts escape, it spells trouble for everyoneâ€¦ Fantastic Beasts and Where to Find Them marks the screenwriting debut of J.K. Rowling, author of the beloved and internationally bestselling Harry Potter books. Featuring a cast of remarkable characters, this is epic, adventure-packed storytelling at its very best.");
INSERT INTO book VALUES ('978-1455539741','Hamilton: The Revolution',16,'2016-04-12 00:00:00',288,'Arts & Photography',45.00,25.73,20.58,'.png','2018-01-01 00:00:00',0,'HAMILTON: THE REVOLUTION gives readers an unprecedented view of both revolutions, from the only two writers able to provide it. Miranda, along with Jeremy McCarter, a cultural critic and theater artist who was involved in the project from its earliest stages--"since before this was even a show," according to Miranda--traces its development from an improbable perforÂ­mance at the White House to its landmark opening night on Broadway six years later. In addition, Miranda has written more than 200 funny, revealing footnotes for his award-winning libretto, the full text of which is published here.');
INSERT INTO book VALUES ('978-1408711392','Fire and Fury: Inside the Trump White House ',7,'2018-02-16 00:00:00',328,'History',22.31,16.69,14.99,'.png','2018-01-01 00:00:00',0,"With extraordinary access to the West Wing, Michael Wolff reveals what happened behind-the-scenes in the first nine months of the most controversial presidency of our time in Fire and Fury: Inside the Trump White House.");

CREATE TABLE Author_Book (
    author_id int NOT NULL,
    isbn varchar(14) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES Author(id),
    FOREIGN KEY (isbn) REFERENCES Book(isbn)
);

INSERT INTO Author_Book VALUES (1,"978-1449474256");
INSERT INTO Author_Book VALUES (2,"978-0375869020");
INSERT INTO Author_Book VALUES (3,"978-0545392556");
INSERT INTO Author_Book VALUES (4,"978-0062457714");
INSERT INTO Author_Book VALUES (5,"978-1419725456");
INSERT INTO Author_Book VALUES (6,"978-1623156121");
INSERT INTO Author_Book VALUES (7,"978-0062300546");
INSERT INTO Author_Book VALUES (8,"978-0393609394");
INSERT INTO Author_Book VALUES (9,"978-0312510787");
INSERT INTO Author_Book VALUES (10,"978-0544609716");
INSERT INTO Author_Book VALUES (11,"978-0385490818");
INSERT INTO Author_Book VALUES (12,"978-0802412706");
INSERT INTO Author_Book VALUES (13,"978-0762447695");
INSERT INTO Author_Book VALUES (14,"978-1501175565");
INSERT INTO Author_Book VALUES (15,"978-0679805274");
INSERT INTO Author_Book VALUES (16,"978-1455570249");
INSERT INTO Author_Book VALUES (17,"978-0451524935");
INSERT INTO Author_Book VALUES (18,"978-0671027032");
INSERT INTO Author_Book VALUES (19,"978-0385514231");
INSERT INTO Author_Book VALUES (20,"978-0545935203");
INSERT INTO Author_Book VALUES (21,"978-1101883082");
INSERT INTO Author_Book VALUES (1,"978-1449486792");
INSERT INTO Author_Book VALUES (22,"978-1416947370");
INSERT INTO Author_Book VALUES (23,"978-1878424310");
INSERT INTO Author_Book VALUES (24,"978-0671449025");
INSERT INTO Author_Book VALUES (25,"978-1595620156");
INSERT INTO Author_Book VALUES (26,"978-0385376716");
INSERT INTO Author_Book VALUES (27,"978-0964729230");
INSERT INTO Author_Book VALUES (28,"978-1607747307");
INSERT INTO Author_Book VALUES (29,"978-1524732684");
INSERT INTO Author_Book VALUES (30,"978-1524732684");
INSERT INTO Author_Book VALUES (31,"978-1623363581");
INSERT INTO Author_Book VALUES (32,"978-0316512589");
INSERT INTO Author_Book VALUES (33,"978-1433805615");
INSERT INTO Author_Book VALUES (34,"978-0670026197");
INSERT INTO Author_Book VALUES (35,"978-0694003617");
INSERT INTO Author_Book VALUES (36,"978-0694003617");
INSERT INTO Author_Book VALUES (37,"978-1595141712");
INSERT INTO Author_Book VALUES (38,"978-1501139154");
INSERT INTO Author_Book VALUES (39,"978-0374360979");
INSERT INTO Author_Book VALUES (53,"978-0545791342");
INSERT INTO Author_Book VALUES (40,"978-0374300210");
INSERT INTO Author_Book VALUES (41,"978-0805047905");
INSERT INTO Author_Book VALUES (42,"978-0786965601");
INSERT INTO Author_Book VALUES (43,"978-1419708459");
INSERT INTO Author_Book VALUES (44,"978-1627790642");
INSERT INTO Author_Book VALUES (45,"978-0803736801");
INSERT INTO Author_Book VALUES (46,"978-1595555274");
INSERT INTO Author_Book VALUES (47,"978-1501100109");
INSERT INTO Author_Book VALUES (48,"978-0545795661");
INSERT INTO Author_Book VALUES (49,"978-1623158088");
INSERT INTO Author_Book VALUES (50,"978-1592408412");
INSERT INTO Author_Book VALUES (51,"978-1501124020");
INSERT INTO Author_Book VALUES (52,"978-1455540419");
INSERT INTO Author_Book VALUES (60,"978-1423160939");
INSERT INTO Author_Book VALUES (53,"978-0545790352");
INSERT INTO Author_Book VALUES (54,"978-0399226908");
INSERT INTO Author_Book VALUES (55,"978-0553522778");
INSERT INTO Author_Book VALUES (56,"978-0800788032");
INSERT INTO Author_Book VALUES (57,"978-1328683786");
INSERT INTO Author_Book VALUES (58,"978-1973124269");
INSERT INTO Author_Book VALUES (59,"978-1973124269");
INSERT INTO Author_Book VALUES (60,"978-1484746424");
INSERT INTO Author_Book VALUES (61,"978-0553496673");
INSERT INTO Author_Book VALUES (62,"978-1591846444");
INSERT INTO Author_Book VALUES (63,"978-0393609097");
INSERT INTO Author_Book VALUES (64,"978-1503943377");
INSERT INTO Author_Book VALUES (65,"978-1400205875");
INSERT INTO Author_Book VALUES (10,"978-0544854413");
INSERT INTO Author_Book VALUES (66,"978-1609580834");
INSERT INTO Author_Book VALUES (67,"978-0743247542");
INSERT INTO Author_Book VALUES (68,"978-0743273565");
INSERT INTO Author_Book VALUES (69,"978-0374533557");
INSERT INTO Author_Book VALUES (70,"978-0307887443");
INSERT INTO Author_Book VALUES (20,"978-0545935210");
INSERT INTO Author_Book VALUES (71,"978-1451639612");
INSERT INTO Author_Book VALUES (72,"978-1250138064");
INSERT INTO Author_Book VALUES (73,"978-0385383691");
INSERT INTO Author_Book VALUES (79,"978-0385541176");
INSERT INTO Author_Book VALUES (74,"978-0060256654");
INSERT INTO Author_Book VALUES (75,"978-1524741723");
INSERT INTO Author_Book VALUES (76,"978-0804190114");
INSERT INTO Author_Book VALUES (77,"978-0062315007");
INSERT INTO Author_Book VALUES (78,"978-1451673319");
INSERT INTO Author_Book VALUES (79,"978-0385543026");
INSERT INTO Author_Book VALUES (80,"978-0525555360");
INSERT INTO Author_Book VALUES (50,"978-1592858491");
INSERT INTO Author_Book VALUES (81,"978-0920668375");
INSERT INTO Author_Book VALUES (82,"978-0062225269");
INSERT INTO Author_Book VALUES (83,"978-0345472328");
INSERT INTO Author_Book VALUES (84,"978-0689835605");
INSERT INTO Author_Book VALUES (85,"978-1594204876");
INSERT INTO Author_Book VALUES (86,"978-0062316097");
INSERT INTO Author_Book VALUES (87,"978-0060555665");
INSERT INTO Author_Book VALUES (88,"978-0062427137");
INSERT INTO Author_Book VALUES (89,"978-0385534246");
INSERT INTO Author_Book VALUES (90,"978-1594634024");
INSERT INTO Author_Book VALUES (91,"978-1338099133");
INSERT INTO Author_Book VALUES (53,"978-1338099133");
INSERT INTO Author_Book VALUES (92,"978-1419723445");
INSERT INTO Author_Book VALUES (44,"978-1627790628");
INSERT INTO Author_Book VALUES (53,"978-0325401126");
INSERT INTO Author_Book VALUES (93,"978-1455539741");
INSERT INTO Author_Book VALUES (94,"978-1408711392");

CREATE TABLE Client (
    id int PRIMARY KEY auto_increment,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    title varchar(10) DEFAULT NULL,
    first_name varchar(255) DEFAULT NULL,
    last_name varchar(255) DEFAULT NULL,
    phone_number varchar(10) DEFAULT NULL,
    manager int DEFAULT NULL,
    company_name varchar(255) DEFAULT NULL,
    address_1 varchar(255) DEFAULT NULL,
    address_2 varchar(255) DEFAULT NULL,
    city varchar(255) DEFAULT NULL,
    province varchar(255) DEFAULT NULL,
    country varchar(255) DEFAULT NULL,
    postal_code varchar(6) DEFAULT NULL,
    FOREIGN KEY (province) REFERENCES Taxes(province)
);

INSERT INTO Client (email, password, title, first_name, last_name, phone_number,
    manager, company_name, address_1, address_2, city, province, country, postal_code) VALUES
("test@test.com", "password", "tester", "John", "Doe", "1234567890", 0, "testers", 
"testaddress1", "testaddress2", "montreal", "quebec", "canada", "1A2B3C");

CREATE TABLE Review (
    id int PRIMARY KEY auto_increment,
    isbn varchar(14) NOT NULL,
    client_id int NOT NULL,
    rating int NOT NULL,
    review_message varchar(2184) NOT NULL,
    approval_status varchar(50) NOT NULL,
    review_date timestamp NOT NULL,
    FOREIGN KEY (isbn) REFERENCES Book(isbn),
    FOREIGN KEY (client_id) REFERENCES Client(id)
);

INSERT INTO Review (isbn, client_id, rating, review_message, approval_status, review_date) VALUES 
('978-1449474256', 1, 4, 'It was aight.', 'Pending', '2018-01-01 22:22:22');

CREATE TABLE Invoice (
    id int PRIMARY KEY auto_increment,
    client_id int NOT NULL,
    date_of_sale timestamp NOT NULL,
    net_value numeric(6,2) NOT NULL,
    gross_value numeric(6,2) NOT NULL,
    removal_status boolean NOT NULL DEFAULT false,
    FOREIGN KEY (client_id) REFERENCES Client(id)
);

INSERT INTO Invoice (client_id, date_of_sale, net_value, gross_value) VALUES
(1, '2017-1-12 12:12:12', 59.96, 75.00);

CREATE TABLE Invoice_Details (
    id int PRIMARY KEY auto_increment,
    invoice_id int NOT NULL,
    isbn varchar(14) NOT NULL,
    book_price numeric(5,2) NOT NULL,
    PST_rate numeric(4,2) NOT NULL,
    GST_rate numeric(4,2) NOT NULL,
    HST_rate numeric(4,2) NOT NULL,
    removal_status boolean NOT NULL DEFAULT false,
    FOREIGN KEY (invoice_id) REFERENCES Invoice(id),
    FOREIGN KEY (isbn) REFERENCES Book(isbn)
);

INSERT INTO Invoice_Details (invoice_id, isbn, book_price, PST_rate, GST_rate, HST_rate) VALUES 
(1, '978-1449474256', 14.99, 0.10, 0.05, 0.15),
(1, '978-0762447695', 14.99, 0.10, 0.05, 0.15),
(1, '978-0545795661', 14.99, 0.10, 0.05, 0.15),
(1, '978-1408711392', 14.99, 0.10, 0.05, 0.15);

CREATE TABLE Survey (
    id int PRIMARY KEY auto_increment,
    question varchar(255) NOT NULL DEFAULT '',
    option1 varchar(255) NOT NULL DEFAULT '',
    option2 varchar(255) NOT NULL DEFAULT '',
    option3 varchar(255) NOT NULL DEFAULT '',
    option4 varchar(255) NOT NULL DEFAULT '',
    count1 int NOT NULL DEFAULT 0,
    count2 int NOT NULL DEFAULT 0,
    count3 int NOT NULL DEFAULT 0,
    count4 int NOT NULL DEFAULT 0,
    removal_status boolean NOT NULL DEFAULT false
);
INSERT INTO Survey(question, option1, option2, option3, option4, count1, count2, count3, count4, removal_status) VALUES 
("Do you like Horror?", "Very Much", "Yes", "No", "I hate it", 0, 0, 0, 0,true);

DROP TABLE IF EXISTS Ads;

CREATE TABLE Ads (
id int PRIMARY KEY auto_increment,
imageName varchar(255) NOT NULL,
siteLink varchar(255) NOT NULL);

INSERT INTO Ads (imageName,siteLink) VALUES
("gillette.jpg", "https://gillette.ca");
INSERT INTO Ads (imageName,siteLink) VALUES
("gusta.png", "http://gustafoods.com");
INSERT INTO Ads (imageName,siteLink) VALUES
("mountainDew.jpg", "http://www.mountaindew.com");
INSERT INTO Ads (imageName,siteLink) VALUES
("museumFineArts.jpg", "https://www.mbam.qc.ca/en/");
INSERT INTO Ads (imageName,siteLink) VALUES
("macbook.jpg", "https://www.apple.com/ca/macbook/");


DROP TABLE IF EXISTS News;

CREATE TABLE News (
id int PRIMARY KEY auto_increment,
readerLink varchar(255) NOT NULL,
activeStatus boolean NOT NULL DEFAULT false);

INSERT INTO News (readerLink,activeStatus) VALUES
("http://rss.cbc.ca/lineup/topstories.xml",true);