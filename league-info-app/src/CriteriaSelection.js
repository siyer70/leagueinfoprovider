import React from 'react';

class CriteriaSelection extends React.Component {
  constructor(props) {
    super(props);
    this.state = { selectedCriteria: '', criteriaList: [] };
    this.handleChange = this.handleChange.bind(this);
  }

  componentDidMount() {
    const apiURLPrefix = process.env.REACT_APP_API_URL_PREFIX;
    const criteriaURL = process.env.REACT_APP_API_CRITERIA_URL;
    const apiURL = `${apiURLPrefix}${criteriaURL}`;
    const { setCriteriaLoadingStatus } = this.props;
    setCriteriaLoadingStatus(true);
    fetch(apiURL)
      .then(response => {
        return response.json();
      })
      .then(data => {
        const criteraListFromAPI = data.map(clist => {
          const vstr = clist.countryName.concat(
            '|',
            clist.leagueName,
            '|',
            clist.teamName
          );
          const dstr = 'Country='.concat(
            clist.countryName,
            ' League=',
            clist.leagueName,
            ' Team=',
            clist.teamName
          );
          return { value: vstr, display: dstr };
        });
        this.setState({
          criteriaList: [
            { value: '', display: '(Select criteria from the list)' }
          ].concat(criteraListFromAPI)
        });
        setCriteriaLoadingStatus(false);
      })
      .catch(error => {
        console.log(error);
      });
  }

  handleChange(event) {
    const { postChangeTrigger } = this.props;
    this.setState({ selectedCriteria: event.target.value });
    postChangeTrigger(event.target.value);
  }

  render() {
    const { criteriaList, selectedCriteria } = this.state;
    return (
      <form>
        <div className="Criteria">
          <label htmlFor="clist-id">
            Select Country, League and Team:&nbsp;
            <select
              id="clist-id"
              name="clist"
              value={selectedCriteria}
              onChange={this.handleChange}
            >
              {criteriaList.map(criteria => (
                <option key={criteria.value} value={criteria.value}>
                  {criteria.display}
                </option>
              ))}
            </select>
          </label>
        </div>
      </form>
    );
  }
}

export default CriteriaSelection;
