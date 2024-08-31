import React, { useState } from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import axios from 'axios';


function SignUpForm() {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
    });

    const [errors, setErrors] = useState({});
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const validationErrors = validate(formData);
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
        } else {
            try {
                const response = await axios.post('https://your-api.com/sign-up', formData); // dummy api

                console.log('Sign up response:', response.data);

                // 성공 메시지 표시
                setMessage('Login successful!');

                // 이후 작업 수행 (예: 리다이렉션)
            } catch (error) {
                console.error('Login error:', error);
                setMessage('Login failed. Please check your credentials.');
            }
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const validate = (values) => {
        const errors = {};
        if (!values.username) errors.username = 'Username is required';
        if (!values.email) errors.email = 'Email is required';
        if (!values.password) errors.password = 'Password is required';
        return errors;
    };

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: '0vh' }}>
            <Row>
                <Col>
                    <Form onSubmit={handleSubmit} className="p-4 border rounded">
                        <h2 className="text-center mb-4">Sign Up</h2>

                        <Form.Group controlId="username">
                            <Form.Label>Username</Form.Label>
                            <Form.Control
                                type="text"
                                name="username"
                                value={formData.username}
                                onChange={handleChange}
                                isInvalid={!!errors.username}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.username}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group controlId="email" className="mt-3">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                isInvalid={!!errors.email}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.email}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group controlId="password" className="mt-3">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                isInvalid={!!errors.password}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.password}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Button type="submit" variant="primary" className="mt-4 w-100">
                            Sign Up
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
}

export default SignUpForm;
