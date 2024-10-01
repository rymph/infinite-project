import React, { useState } from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import axios from 'axios';

function LoginForm() {
    const [formData, setFormData] = useState({
        userId: '',
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
                const response = await axios.post('api', formData); //dummy api

                // 로그인 성공 시 토큰 저장
                const token = response.headers['authorization'];
                localStorage.setItem('accessToken', token);

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
        if (!values.userId) errors.userId = 'ID is required';
        if (!values.password) errors.password = 'Password is required';
        return errors;
    };

    const handleAuthRequest = async () => {
        const token = localStorage.getItem('accessToken');

        if (!token) {
            console.error('No token found');
            return;
        }

        try {
            const response = await axios.get('api', { //dummy api
                headers: {
                    'Authorization': `${token}`,
                },
            });

            console.log('Response:', response);
        } catch (error) {
            console.error('Error during API request:', error);
        }
    };


    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
            <Row>
                <Col>
                    <Form onSubmit={handleSubmit} className="p-4 border rounded">
                        <h2 className="text-center mb-4">Login</h2>

                        {message && <p className="text-center">{message}</p>}

                        <Form.Group controlId="userId">
                            <Form.Label>ID</Form.Label>
                            <Form.Control
                                type="text"
                                name="userId"
                                value={formData.userId}
                                onChange={handleChange}
                                isInvalid={!!errors.userId}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.userId}
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
                            Login
                        </Button>
                        <Button
                            onClick={handleAuthRequest}
                            variant="secondary"
                            className="mt-3 w-100"
                        >
                            Send Auth Request
                        </Button>
                        <div className="text-center mt-3">
                            <Link to="/signup">Don't have an account? Sign Up</Link>
                        </div>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
}

export default LoginForm;
