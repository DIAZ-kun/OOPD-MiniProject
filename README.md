# Mumbai Metro Ticketing System

This Java console application simulates a simplified Mumbai Metro ticketing system with a unified QR-code-based ticket booking and validation workflow.

## What the project does

- Allows passengers to register or log in.
- Lets passengers manage a digital wallet with recharge support.
- Enables passengers to book metro tickets for two metro operators:
  - **MMRDA**
  - **Reliance**
- Supports a unified interchange option with a common station (`Gundavali`) between the two routes.
- Generates tickets containing QR codes and fare details.
- Allows operator staff to scan QR codes on each line and update ticket flow through `ENTRY`, `EXIT`, and final `USE` actions.
- Supports transfer journeys where a passenger must exit one line and then re-enter the next line.
- Stores ticket history for each passenger.

## Main actors

- **Passenger**: registers, logs in, checks balance, recharges wallet, books tickets, and views booking history.
- **MMRDA Operator**: validates only the MMRDA segment of a ticket and processes entry/exit/use actions.
- **Reliance Operator**: validates only the Reliance segment of a ticket and processes entry/exit/use actions.

## Key components

- `Main.java`: application entry point and CLI menu flow.
- `model/Passenger.java`: passenger profile, wallet, and booking history.
- `model/Ticket.java`: ticket data structure including operator, source, destination, fare, and QR status.
- `operator/MMRDAOperator.java` and `operator/RelianceOperator.java`: operator QR validation logic.
- `registry/PassengerRegistry.java`: in-memory storage for passenger accounts.
- `registry/TicketRegistry.java`: in-memory storage for issued tickets.
- `enums/QRStatus.java`: QR ticket status values like `ACTIVE` and `USED`.

## How it works

1. Passenger registers or logs in through the console menu.
2. Passenger can recharge their wallet balance.
3. Passenger chooses an operator line or unified interchange route.
4. The system calculates fare based on station distance and creates a ticket.
5. Ticket QR code is saved and can be validated by the operator.
6. Operator scans the QR code; the system automatically determines the action based on scan count:
   - 1st scan: `ENTERED`
   - 2nd scan: `USED` (exit and mark as used)
7. For a transfer journey, the passenger exits the first line and then re-enters the second line, with each line's segment validated independently.

## Run instructions

From the project root:

```bash
javac src/**/*.java
java -cp src Main
```

> Note: `Main` is in the default package, so the classpath must point to `src`.

## Notes

- This is a simple in-memory simulation, so all data is lost when the program exits.
- The fare model is based on the number of station hops multiplied by a fixed rate.
- Operator login credentials are hard-coded in `Main.java`.

