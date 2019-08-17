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
    var baseUrl = process.env.REACT_APP_API_URL_PREFIX;
    var positionUrl = baseUrl + "/api/position/query?country_name={0}&league_name={1}&team_name={2}";
    positionUrl = positionUrl.replace("{0}", countryName).replace("{1}", leagueName).replace("{2}", teamName);
    console.log(positionUrl);
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
        <header>
          <div>
            <div class="headerrow" align="center">
              <span>Know your favorite football team position in league</span>
            </div>
            <img class="imgclass" src="football_splash.jpeg" alt="Splash Image" width="100%"/>
          </div>
        </header>
        <CriteriaSelection postChangeTrigger={this.updateResultPostChange.bind(this)} {...this.props}/>
        <br/>
        <br/>
        <br/>
        <Result positionData={this.state.positionData}/>
        <footer>
          <div class="footer">
              <div class="footerrow" align="right">
                <span>&copy; 2019 Some company Inc. All Rights Reserved &nbsp;</span>
                <span class="tab">|</span>
                <span class="tab">Sitemap</span>
                <span class="tab">|</span>
                <span class="tab">Privacy Policy</span>
              </div>
          </div>
        </footer>        
      </div>
    );
  }
}

export default App;
