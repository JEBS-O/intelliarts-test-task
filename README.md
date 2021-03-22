## Intelliarts test task | Personal expenses  
### How to run and build the project
1. Clone project  
` $ git clone https://github.com/JEBS-O/intelliarts-test-task.git`
2. In __application.properties__ file you should set __spring.datasource.url__, __spring.datasource.username__, __spring.datasource.password__ values for your postreSQL database.
3. In order to run tests you should set the same properties in __test-application.properties__ in test folder. This have to be other database for test cases.
4. For run tests you can use command  
`mvn test`  .
5. For compile and run an application you can use command  
`mvn spring-boot:run`  
Or  
`mvn clean package`  
`java -jar target/*.jar`

### Endpoints
__POST [host]/expenses__ — adds expense entry to the list of user
expenses. Expenses for various dates could be added in any
order. Endpoint accepts JSON with the following data:
- date — is the date when expense occurred
- amount — is an amount of money spent
- currency — the currency in which expense occurred
- product — is the name of product purchased  

__GET [host]/expenses__ — shows the list of all expenses grouped
and sorted by date  

__DELETE [host]/expenses?date=2021-04-25__ — removes all
expenses for specified date, where:
- date — is the date for which all expenses should be removed  

__GET [host]/total?currency=PLN__ — this command take a list of
exchange rates from https://exchangerate.host and calculate the total amount of money spent and present it
to user in specified currency, where:
- currency — is the currency in which total amount of expenses should
be presented