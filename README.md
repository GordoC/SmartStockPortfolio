# Smart Stock Portfolio

## Gordon Chieng

This application allows users to **keep track** of the stocks
in their possession and **calculates** the profit/loss they are at.

Users are those who invest into stocks such as investors,
hedge fund managers, etc.
 
This project is of interest to me because I have many stocks
in different online brokerage accounts. It has been
a hassle to go to each site and to check what I have. Furthermore,
it is tedious to calculate the profit/loss I 
currently have especially when they are in different
currencies.


Features of the application that users can access includes:
- Storage of the name and quantity of the stock
- Calculate the profit/loss of the portfolio, or a certain stock
- Edits to their portfolio

#### User's Stories:

- As a user, I want to be able to add a stock to my portfolio (add multiple X's to a Y)
- As a user, I want to be able to delete a stock from my portfolio
- As a user, I want to be able to view the stocks in my portfolio
- As a user, I want to be able to see the profit/loss each stock in my portfolio is at
- As a user, I want to be able to see the total profit/loss I am at in my portfolio
- As a user, I want to be able to edit each stock in my portfolio manually
- As a user, when I select the quit option, I want to be reminded to save my portfolio to a file
 and have the option to do so or not.
- As a user, I want to be able to save my portfolio to a file
- As a user, I want to be able to load my portfolio from a file 

#### Phase 4: Task 2

I chose the first option, which is testing and designing a class that is robust.
The classes that have a robust design are:
- Portfolio, the method is 'addStock' 

- Stock, the two constructors and the two methods, 'addVolume', and 'subtractVolume'

#### Phase 4: Task 3

I like the design of my UML class diagram, although it was tough
to make the arrows attaching each class look clear and not overlap each other
too much. I find that there was a good balance of coupling and cohesion in my design.

If I had more time to work on the project, I would probably
refactor:
- How there is an association between PortfolioAppGraphics and Stock,
I don't think there needs to be one but it seemed complex to fix

- My graphic classes. I believe there's bad coupling and/or cohesion in there
somewhere with the J stuff (JFrame, Jlabel, etc) but I'm not too experienced 
right now to refactor them.

- Add more helper methods because some methods might be doing more than one
task

Other than that, I think my UML class diagram and code is good