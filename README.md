# Insider - QA Engineer Case Study

---

## Overview

This repository contains an automation framework that may provide end-to-end testing of a Insider web application.

The framework utilizes:
- **Selenium WebDriver** for browser automation.
- **TestNG** as the testing framework.
- **Page Object Model** to make the code more readable and maintainable.
- **Maven** for managing project dependencies and running tests.
- **Extent Report**  as the reporting tool.

---


## Getting Started

### Prerequisites

- Java 11+
- Selenium 4+
- Maven 3.6+
- TestNG 7+
- Latest stable browser version of Chrome WebDriver.

---

### Installation

#### Step-1: Clone the repository
git clone https://github.com/efisek/emre_fisek_case.git

cd emre_fisek_case

#### Step-2: Install dependencies
mvn clean install

#### Step-3: Run tests
mvn test

---

### Key Components:

- **BaseTest**: Contains setup, teardown logic for WebDriver, captures screenshots on failed tests.
- **BasePage**: It works as a foundation of the other pages, including common methods to interact with web elements. 
- Reports and screenshots can be found under the **test-output** folder.
- The source code is in **master** branch.

---

## License

This project is licensed under the MIT License.

---

## Contributors

Emre Fişek
