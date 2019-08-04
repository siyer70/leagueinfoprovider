import React from 'react';
import './index.css';
import './App.css';
import CriteriaSelection from './CriteriaSelection';
import Result from './Result';
class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {positionData: {countryInfo:'', leagueInfo:'', teamInfo:'', position:0}};
  }

  updateResultPostChange(selection) {
    if(selection.indexOf("|") < 0) {
      this.setState({ positionData: {countryInfo:'', leagueInfo:'', teamInfo:'', position:0} });
      return;
    }
    var splitCriteria = selection.split("|");
    var countryName = splitCriteria[0].replace(" ", "%20");
    var leagueName = splitCriteria[1].replace(" ", "%20");
    var teamName = splitCriteria[2].replace(" ", "%20");
    var positionUrl = "http://localhost:8080/api/position/query?country_name={0}&league_name={1}&team_name={2}";
    positionUrl = positionUrl.replace("{0}", countryName).replace("{1}", leagueName).replace("{2}", teamName);
    this.loadResult(positionUrl);
  }

  loadResult = function(positionUrl) {
    fetch(positionUrl)
      .then((response) => {
        return response.json();
      })
      .then(data => {
        let cinfo = "(".concat(data.country_id, ")", " - ", data.country_name);
        let linfo = "(".concat(data.league_id, ")", " - ", data.league_name);
        let tinfo = "(".concat(data.team_id, ")", " - ", data.team_name);
        let pos = data.overall_league_position;
        this.setState({ positionData: {countryInfo:cinfo, leagueInfo:linfo, teamInfo:tinfo, position:pos} });
      }).catch(error => {
        console.log(error);
      });
  }

  render() {
    return (
      <div className="App">
        <CriteriaSelection postChangeTrigger={this.updateResultPostChange.bind(this)} {...this.props}/>
        <br/>
        <br/>
        <br/>
        <Result positionData={this.state.positionData}/>
      </div>
    );
  }
}

export default App;
