import React from 'react';
import './index.css';
import './App.css';
import PageHeader from './Header';
import PageFooter from './Footer';
import CriteriaSelection from './CriteriaSelection';
import Result from './Result';
import { Rolling } from 'react-loading-io';
class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: false,
      positionData: {countryInfo:'', leagueInfo:'', teamInfo:'', position:0}};
  }

  setCriteriaLoadingStatus(isLoading) {
    this.setState({loading:isLoading});
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
    this.setState({loading: true});
    fetch(positionUrl)
      .then((response) => {
        return response.json();
      })
      .then(data => {
        let cinfo = "(".concat(data.country_id, ")", " - ", data.country_name);
        let linfo = "(".concat(data.league_id, ")", " - ", data.league_name);
        let tinfo = "(".concat(data.team_id, ")", " - ", data.team_name);
        let pos = data.overall_league_position;
        this.setState({ loading: false, positionData: {countryInfo:cinfo, leagueInfo:linfo, teamInfo:tinfo, position:pos} });
      }).catch(error => {
        console.log(error);
      });
  }

  render() {
    let element;
    if(this.state.loading) {
      element = <div align="center"><Rolling size={64} color="crimson"/></div>
    } else {
      element = <br/>
    }
    return (
      <div className="App">
        <PageHeader />
        <CriteriaSelection setCriteriaLoadingStatus={this.setCriteriaLoadingStatus.bind(this)} 
              postChangeTrigger={this.updateResultPostChange.bind(this)} 
              {...this.props}/>
        {element}
        <Result positionData={this.state.positionData}/>
        <PageFooter />
      </div>
    );
  }
}

export default App;
