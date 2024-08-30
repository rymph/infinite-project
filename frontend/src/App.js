import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import LoginForm from './screen/LoginForm';
import React, { useState } from 'react';
import SignUpForm from './screen/SignUpForm';
import { Button, Container } from 'react-bootstrap';

function App() {
  const [isSignUp, setIsSignUp] = useState(false);

  return (
    <Container className="d-flex flex-column align-items-center justify-content-center" style={{ minHeight: '100vh' }}>
      {isSignUp ? <SignUpForm /> : <LoginForm />}
      <Button
        variant="link"
        onClick={() => setIsSignUp(!isSignUp)}
        className="w-100 mt-3"
      >
        {isSignUp ? 'Already have an account? Login' : 'Don\'t have an account? Sign Up'}
      </Button>
    </Container>
  );
}

export default App;


