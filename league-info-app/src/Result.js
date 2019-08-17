import React from 'react';
import './App.css';

class Result extends React.Component {
  constructor(props) {
    super(props);
    console.log(props);
  }

  render() {
    return (
      <table align="center">
        <tbody>
          <tr>
            <td>Country ID & Name: </td>
            <td>{this.props.positionData.countryInfo}</td>
          </tr>
          <tr>
            <td>League ID & Name: </td>
            <td>{this.props.positionData.leagueInfo}</td>
          </tr>
          <tr>
            <td>Team ID & Name: </td>
            <td>{this.props.positionData.teamInfo}</td>
          </tr>
          <tr>
            <td>Overall League Position: </td>
            <td>{this.props.positionData.position}</td>
          </tr>
        </tbody>
      </table>
    );
  }
}

export default Result;
