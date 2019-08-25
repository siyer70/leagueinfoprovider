import React from 'react';
import { Rolling } from 'react-loading-io';
import './index.css';
import './App.css';
import PageHeader from './Header';
import PageFooter from './Footer';
import CriteriaSelection from './CriteriaSelection';
import Result from './Result';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.setCriteriaLoadingStatus = this.setCriteriaLoadingStatus.bind(this);
    this.updateResultPostChange = this.updateResultPostChange.bind(this);
    this.state = {
      loading: false,
      positionData: {
        countryInfo: '',
        leagueInfo: '',
        teamInfo: '',
        position: 0
      }
    };
  }

  setCriteriaLoadingStatus(isLoading) {
    this.setState({ loading: isLoading });
  }

  loadResult(positionUrl) {
    this.setState({ loading: true });
    fetch(positionUrl)
      .then(response => {
        return response.json();
      })
      .then(data => {
        const cinfo = '('.concat(
          data.country_id,
          ')',
          ' - ',
          data.country_name
        );
        const linfo = '('.concat(data.league_id, ')', ' - ', data.league_name);
        const tinfo = '('.concat(data.team_id, ')', ' - ', data.team_name);
        const pos = data.overall_league_position;
        this.setState({
          loading: false,
          positionData: {
            countryInfo: cinfo,
            leagueInfo: linfo,
            teamInfo: tinfo,
            position: pos
          }
        });
      })
      .catch(error => {
        // eslint-disable-next-line no-console
        console.error(error);
      });
  }

  updateResultPostChange(selection) {
    if (selection.indexOf('|') < 0) {
      this.setState({
        positionData: {
          countryInfo: '',
          leagueInfo: '',
          teamInfo: '',
          position: 0
        }
      });
      return;
    }
    const splitCriteria = selection.split('|');
    const countryName = splitCriteria[0].replace(' ', '%20');
    const leagueName = splitCriteria[1].replace(' ', '%20');
    const teamName = splitCriteria[2].replace(' ', '%20');
    const baseUrl = process.env.REACT_APP_API_URL_PREFIX;
    const positionURL = process.env.REACT_APP_API_POSITION_URL;
    let positionUrl = `${baseUrl}${positionURL}`;
    positionUrl = positionUrl
      .replace('{0}', countryName)
      .replace('{1}', leagueName)
      .replace('{2}', teamName);
    this.loadResult(positionUrl);
  }

  render() {
    let element;
    const { loading, positionData } = this.state;
    if (loading) {
      element = (
        <div align="center">
          <Rolling size={64} color="crimson" />
        </div>
      );
    } else {
      element = <br />;
    }
    return (
      <div className="App">
        <PageHeader />
        <CriteriaSelection
          setCriteriaLoadingStatus={this.setCriteriaLoadingStatus}
          postChangeTrigger={this.updateResultPostChange}
        />
        {element}
        <Result positionData={positionData} />
        <PageFooter />
      </div>
    );
  }
}

export default App;
