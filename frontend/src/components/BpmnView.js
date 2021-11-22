import ReactBpmn from 'react-bpmn';
import file from '../job-application.bpmn'

function onShown() {
    console.log('diagram shown');
  }

  function onLoading() {
    console.log('diagram loading');
  }

  function onError(err) {
    console.log('failed to show diagram');
  }

  async function analyzeDiagram() {
    var formData = new FormData();
    formData.append('file',file);
    const requestOptions = {
        method: 'POST',
        //headers: { 'Content-Type': 'multipart/form-data;boundary=TEST' },
        body: formData
    };
    const response = await fetch('/analyze', requestOptions);
    const data = await response.json();
    console.log(data)
  }

const BpmnView = () => {
    return (
    <div>
        <ReactBpmn
      url="/bpmn/job-application.bpmn"
      onShown={ onShown }
      onLoading={ onLoading }
      onError={ onError }
    />
    <button onClick={async () => {await analyzeDiagram();} } className="btn">ANALYZE DIAGRAM</button>
    </div>
    )
}

export default BpmnView
