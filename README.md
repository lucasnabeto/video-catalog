# VideoCatalog

A Spring Boot application for searching and browsing TV show information. This project demonstrates the integration of external APIs, data conversion, and stream processing in Java.

## Description

VideoCatalog is a command-line application that allows users to search for TV shows and retrieve detailed information including seasons and episodes. The application consumes data from the OMDB (Open Movie Database) API and processes it to display comprehensive information about requested TV shows.

## Features

- **TV Show Search**: Search for TV shows by name
- **Episode Information**: Browse all episodes across all seasons
- **Episode Rating Analysis**: View episode ratings and identify the highest-rated episode
- **Data Processing**: Utilizes Java streams for efficient data filtering and sorting
- **API Integration**: Consumes external OMDB API for real-time TV show data

## Technology Stack

- **Java 25**: Latest Java LTS version
- **Spring Boot 3.5.7**: Framework for building the application
- **Jackson**: JSON serialization/deserialization library
- **Maven**: Build and dependency management

## Project Structure

```
src/main/java/com/learning/videocatalog/
├── VideocatalogApplication.java    # Main Spring Boot application entry point
├── model/
│   ├── Episode.java                # Episode data model
│   ├── TvShowData.java             # TV show information model
│   ├── TvShowEpisodeData.java      # Episode details model
│   └── TvShowSeasonData.java       # Season information model
├── principal/
│   └── Principal.java              # Main application logic and user interaction
└── service/
    ├── ConsumeAPI.java             # API consumption service
    ├── ConvertData.java            # Data conversion interface
    └── ConvertDataImpl.java        # Data conversion implementation
```

## Getting Started

### Prerequisites

- Java 25 or higher
- Maven 3.6 or higher

### Installation

1. Clone or download this repository
2. Navigate to the project directory:
   ```bash
   cd VideoCatalog
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```

### Running the Application

Execute the following Maven command:

```bash
mvn spring-boot:run
```

## Usage

1. When the application starts, you will be prompted to enter a TV show name
2. Enter the name of the TV show you want to search for (e.g., "Arrow", "Breaking Bad")
3. The application will fetch and display:
   - TV show details (title, plot, seasons, etc.)
   - All seasons information
   - All episodes across all seasons
   - Episode ratings analysis

## Notes

This is a learning project created to understand Spring Framework and Java 25 best practices, including:
- Spring Boot application development
- RESTful API consumption
- JSON data processing with Jackson
- Java Stream API usage
- Data model design with records

