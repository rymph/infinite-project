import React, { useState } from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import axios from 'axios';
import { useNavigate,Link } from 'react-router-dom';


function SignUpForm() {
    const [formData, setFormData] = useState({
        userId: '',
        password: '',
    });

    const [errors, setErrors] = useState({});
    const [message, setMessage] = useState('');
    const navigate = useNavigate();  // useNavigate 훅 사용


    const handleSubmit = async (e) => {
        e.preventDefault();
        const validationErrors = validate(formData);
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
        } else {
            try {
                const response = await axios.post('api', formData); //dummy api

                console.log('Sign up response:', response.data);

                // 성공 메시지 표시
                setMessage('signUp successful!');
                console.log(message);
                navigate('/login');


                // 이후 작업 수행 (예: 리다이렉션)
            } catch (error) {
                console.error('signUp error:', error);
                setMessage('signUp failed. Please check your credentials.');
                console.log(message);
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
        if (!values.userId) errors.userId = 'userId is required';
        if (!values.password) errors.password = 'Password is required';
        return errors;
    };

    const SocialKakao = async() => {
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

    const SocialNaver = async() => {
        window.location.href = "/oauth2/authorization/naver"; // Naver OAuth URL로 리디렉션
    }

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
            <Row>
                <Col>
                    <Form onSubmit={handleSubmit} className="p-4 border rounded">
                        <h2 className="text-center mb-4">Sign Up</h2>

                        <Form.Group controlId="userId" className="mt-3">
                            <Form.Label>User Id</Form.Label>
                            <Form.Control
                                type="euserId"
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
                            Sign Up
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
                            <Link to="/login">Already have an account? Login</Link>
                        </div>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
}

export default SignUpForm;
