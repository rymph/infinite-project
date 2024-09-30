import React, { useState} from 'react';
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

    const SocialKakao = async () => {
        // const Rest_api_key = 'api key' //REST API KEY
        // const redirect_uri = 'uri' //Redirect URI
        // // oauth 요청 URL
        // const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${Rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`
        // const handleLogin = () => {
        //     window.location.href = kakaoURL
        // }

        // return handleLogin;

        try {
            const response = await axios.get('api'); // KaKao API 요청
            console.log('KaKao login response:', response.data);
            // KaKao 로그인 후 동작 수행
        } catch (error) {
            console.error('KaKao login error:', error);
        }
    }

    const SocialNaver = async () => {
        window.location.href = "/oauth2/authorization/naver"; // Naver OAuth URL로 리디렉션
    }


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
                        <div className="d-flex align-items-center my-4">
                            <div className="flex-grow-1 border-top"></div> {/* 구분선 */}
                            <span className="mx-3 text-muted">간편로그인</span>
                            <div className="flex-grow-1 border-top"></div> {/* 구분선 */}
                        </div>

                        {/* OAuth 버튼들 */}
                        <div className="d-flex justify-content-center">
                            <Button className="p-3 mx-2 d-flex align-items-center justify-content-center shadow" style={{ width: '60px', height: '60px', border: 0, backgroundColor: 'white' }}>
                                <img src="/google-logo.svg" alt="Google" style={{ width: '24px', height: '24px' }} />
                            </Button>

                            <Button onClick={SocialKakao} className="p-3 mx-2 d-flex align-items-center justify-content-center shadow" style={{ width: '60px', height: '60px', border: 0, backgroundColor: '#FEE500' }}>
                                <img src="/KakaoTalk_logo.svg" alt="Kakao" style={{ width: '24px', height: '24px' }} />
                            </Button>

                            <Button onClick={SocialNaver} className="p-3 mx-2 d-flex align-items-center justify-content-center shadow" style={{ width: '60px', height: '60px', border: 0, backgroundColor: '#03C75A' }}>
                                <img src="/btnG_icon_square.png" alt="Naver" style={{ width: '35px', height: '35px' }} />
                            </Button>
                        </div>
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
