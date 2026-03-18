# NotesApp

A React-based notes application built with Vite, designed to manage and organize notes efficiently. This project demonstrates the use of modern web development tools and libraries.

## Tech Stack

### Frontend
- **React**: For building the user interface.
- **React Router**: For managing navigation and routing.
- **Material-UI (MUI)**: For pre-styled components and icons.
- **Axios**: For making HTTP requests.
- **UUID**: For generating unique identifiers.

### Backend
- **JSON Server**: Used as a mock backend to simulate RESTful APIs.

### Development Tools
- **Vite**: A fast build tool for modern web projects.
- **ESLint**: For maintaining code quality and consistency.

## Features
- User authentication with protected routes.
- CRUD operations for notes.
- Archive and trash functionality.
- Responsive design using Material-UI.

## Lessons Learned
- **React Router**: Implementing protected and authenticated routes.
- **State Management**: Managing state effectively across components.
- **Axios**: Handling API requests and responses.
- **Material-UI**: Customizing pre-built components for a consistent design.
- **JSON Server**: Setting up a mock backend for development and testing.

## How to Run
1. Clone the repository.
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the JSON server:
   ```bash
   npx json-server --watch db.json --port 3000
   ```
4. Run the development server:
   ```bash
   npm run dev
   ```
5. Open the app in your browser at `http://localhost:5173`.

## Future Scope
- Add TypeScript for type safety.
- Implement a real backend with authentication.
- Enhance the UI with more animations and transitions.

## Acknowledgments
This project was bootstrapped with Vite and inspired by modern note-taking applications.