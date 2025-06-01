import React, { useContext } from 'react';
import { TextField, TextFieldProps } from '@mui/material';
import { Card, Col } from 'react-bootstrap';

interface Common_CardProps {
  title: string;
  value: number;
  percentage: number;
  icon: string;
  color: string;
}

function Common_Card({title, value, percentage, icon, color }: Common_CardProps) {
  return (
    <Col md={6} xl={3}>
      <Card className="card-social">
          <Card.Body className="border-bottom">
            <div className="row align-items-center justify-content-center">
              <div className="col-auto">
                <i className="fab fa-facebook-f text-primary f-36" />   
              </div>
              <div className="col text-end">
                <h3>{value}</h3>
                <h5 className="text-c-green mb-0">
                     {/* +{percentage}%  */}
                    <span className="text-muted">{title}</span>
                </h5>
              </div>
            </div>
          </Card.Body>
          {/* <Card.Body>
            <div className="row align-items-center justify-content-center card-active">
              <div className="col-6">
                <h6 className="text-center m-b-10">
                  <span className="text-muted m-r-5">Target:</span>35,098
                </h6>
                <div className="progress">
                  <div
                    className="progress-bar progress-c-theme"
                    role="progressbar"
                    style={{ width: '60%', height: '6px' }}
                    aria-valuenow={60}
                    aria-valuemin={0}
                    aria-valuemax={100}
                  />
                </div>
              </div>
              <div className="col-6">
                <h6 className="text-center  m-b-10">
                  <span className="text-muted m-r-5">Duration:</span>350
                </h6>
                <div className="progress">
                  <div
                    className="progress-bar progress-c-theme2"
                    role="progressbar"
                    style={{ width: '45%', height: '6px' }}
                    aria-valuenow={45}
                    aria-valuemin={0}
                    aria-valuemax={100}
                  />
                </div>
              </div>
            </div>
          </Card.Body> */}
        </Card>
      </Col>
  );
}

export default Common_Card;
