import React from 'react';
import './App.css';

const Result = props => {
  const { positionData } = props;
  return (
    <table align="center">
      <tbody>
        <tr>
          <td>Country ID & Name: </td>
          <td>{positionData.countryInfo}</td>
        </tr>
        <tr>
          <td>League ID & Name: </td>
          <td>{positionData.leagueInfo}</td>
        </tr>
        <tr>
          <td>Team ID & Name: </td>
          <td>{positionData.teamInfo}</td>
        </tr>
        <tr>
          <td>Overall League Position: </td>
          <td>{positionData.position}</td>
        </tr>
      </tbody>
    </table>
  );
};

export default Result;
