import React from 'react';
import { Card, ProgressBar } from 'react-bootstrap';

const MentoringHours = ({ taughtHours }) => {
  const progress = taughtHours > 0 ? (taughtHours / 100) * 100 : 0;

  const getProgressVariant = () => {
    if (progress < 50) return 'primary';
    if (progress < 75) return 'warning';
    return 'success';
  };

  return (
    <Card className="mb-4">
      <Card.Body>
        <Card.Title>Mentoring Hours</Card.Title>
        <div>
          <p>
            {taughtHours} out of 100 hours
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

export default MentoringHours;
