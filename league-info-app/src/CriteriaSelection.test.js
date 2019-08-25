import React from 'react';
import { mount, shallow, render } from 'enzyme';
import 'fetch-reply-with';
import CriteriaSelection from './CriteriaSelection';
import requestResponseData from './reqresponsedata';

describe('Criteria Selection Tests', () => {
  let component;

  beforeEach(() => {
    fetch('http://localhost:8080/api/criterialist', {
      method: 'GET',
      replyWith: {
        status: 200,
        body: JSON.stringify(requestResponseData.criteriaListResponse),
        headers: {
          'Content-Type': 'application/json'
        }
      }
    }).then(res => {
      return res.json();
    });
  });

  afterEach(() => {
    component.unmount();
  });

  it('loads criteria list from the mocked backend call', done => {
    const setCriteriaLoadingStatus = jest.fn();
    const postChangeTrigger = jest.fn();
    component = shallow(
      <CriteriaSelection
        setCriteriaLoadingStatus={setCriteriaLoadingStatus}
        postChangeTrigger={postChangeTrigger}
      />
    );

    process.nextTick(() => {
      expect(setCriteriaLoadingStatus).toHaveBeenCalledTimes(2);
      expect(component.state('criteriaList').length).toEqual(3);
      //   component.update();
      //   expect(component.find('#clist-id').length).toEqual(3);
      done();
    });

    // component
    //   .find('#clist-id')
    //   .simulate('keydown', { key: 'ArrowDown', keyCode: 40, which: 40 });
    // component.find('#clist-id').simulate('change', requestResponseData.event);
    // expect(postChangeTrigger).toHaveBeenCalled();
  });
});
