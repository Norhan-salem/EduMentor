import React from 'react';
import { Card, ProgressBar } from 'react-bootstrap';


const AttendedHours = ({ attendedHours }) => {
  const progress = attendedHours > 0 ? (attendedHours / 100) * 100 : 0;

  const getProgressVariant = () => {
    if (progress < 50) return 'primary';
    if (progress < 75) return 'warning';
    return 'success';
  };

  return (
    <Card className="mb-4">
      <Card.Body>
        <Card.Title>Attended Hours</Card.Title>
        <div>
          <p>
            {attendedHours} out of 100 hours attended
          </p>
          <ProgressBar
            now={progress}
            label={`${Math.round(progress)}%`}
            variant={getProgressVariant()}
          />
        </div>
      </Card.Body>
    </Card>
  );
};

export default AttendedHours;

